package es.ja.csaludad.sas.mensapex.rest.interceptor;

import es.ja.csaludad.sas.mensapex.adapter.maco.internal.TicketMacoValidator;
import es.ja.csaludad.sas.mensapex.rest.factory.OperationOutcomeFactory;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.hl7.fhir.r4.model.OperationOutcome;

import java.io.IOException;
import java.util.List;

@Provider
@PreMatching
@Priority(Priorities.USER)
@Singleton
public class TracingRequestFilter implements ContainerRequestFilter {
    public static final String MACO_HEADER = "maco";
    public static final String VERSION_SOPORTADA = "v1.0";
    public static final String VERSION_HEADER = "version";

    //futuros enum
    public static final String MALFORMED_TICKET = "Ticket malformed";
    public static final String EXPIRED_TICKET = "Ticket expired";
    public static final String FUTURE_TICKET = "Future ticket received";
    public static final String PERMISSION_TICKET = "Ticket Permission are not enough";
    public static final String SIGNATURE_TICKET = "Ticket Signature invalid";



    @Inject
    private TicketMacoValidator macoValidator;
    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {

        //LEEMOS CABECERA MACO
        String macoHeader = ctx.getHeaderString(MACO_HEADER);

        //validamos si no viene -> 400
        if (macoHeader == null || macoHeader.isBlank()) {

            OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                    400,
                    "Solicitud incorrecta. El ticket debe venir informado",
                    List.of("http.maco"),
                    "http.maco"
            );

            Response res = Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(operation) //obj
                    .type(MediaType.APPLICATION_JSON)
                    .build();

            ctx.abortWith(res);
            return;

        }

        /*
        * MACO no es null
        * validamos errores 403 y 401
         */
        String error = contentError(macoHeader);

        if(error != null){
             //403 -> EXPIRED_TICKET o PERMISSION_TICKET
             if (error.equals(EXPIRED_TICKET) || error.equals(PERMISSION_TICKET)){

                OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                        403,
                        "No autorizado. Token sin permiso o caducado",
                        List.of("http.maco"),
                        "http.maco"
                );
                Response res = Response
                        .status(Response.Status.FORBIDDEN)
                        .entity(operation) //obj
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                ctx.abortWith(res);
                return;
             }else {
                //401 -> MALFORMED_TICKET, SIGNATURE_TICKET, FUTURE_TICKET
                OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                        401,
                        "No autorizado. Token invalido",
                        List.of("http.maco"),
                        "http.maco"
                );
                Response res = Response.status(Response.Status.UNAUTHORIZED)
                        .entity(operation) //obj
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                ctx.abortWith(res);
                return;
                }
        }
        /*
        * MACO no contiene error
        * Validamos VERSION error 409
         */
        String versionHeader = ctx.getHeaderString(VERSION_HEADER);
        //rellenamos o capturamos ya que version es opcional en APIDOC
        String version = (versionHeader == null || versionHeader.isBlank())
                ? VERSION_SOPORTADA : versionHeader.trim();

        if(!isValidVersionFormat(version)){
            if(!VERSION_SOPORTADA.equals(version)){
                OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                        409,
                        "La versión del servicio no es soportada: " + version,
                        List.of("http.version"),
                        "http.version"
                );

                Response res = Response.status(Response.Status.CONFLICT)
                        .entity(operation)
                        .type(MediaType.APPLICATION_JSON)
                        .build();

                ctx.abortWith(res);
            }
        }


    }
    public String contentError(String firma) {
        String[] parts = firma.split(":");
        String ticket = parts[0];
        String signature = parts[1];

        if (parts.length != 2) {
            return MALFORMED_TICKET;
        }
        return macoValidator.validate(ticket, signature);
    }

    private boolean isValidVersionFormat(String version) {
        if (version == null || version.isBlank()) {
            return false;
        }
        return version.trim().matches("^v\\d\\.\\d$");
    }


}
