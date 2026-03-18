package es.ja.csaludad.sas.mensapex.rest.interceptor;

import es.ja.csaludad.sas.mensapex.adapter.maco.internal.TicketMacoValidator;
import es.ja.csaludad.sas.mensapex.rest.factory.OperationOutcomeFactory;
import es.ja.csaludad.sas.mensapex.rest.interceptor.enums.TicketMacoErrorConstants;
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
import java.util.Set;

@Provider
@PreMatching
@Priority(Priorities.USER)
@Singleton
public class TracingRequestFilter implements ContainerRequestFilter {
    public static final String MACO_HEADER = "maco";
    public static final String VERSION_SOPORTADA = "v1.0";
    public static final String VERSION_HEADER = "version";
    public static final String HTTP_MACO = "http.maco";
    public static final String HTTP_VERSION = "http.version";
    //agrupo errores
    private static final Set<String> FORBIDDEN_ERRORS =
            Set.of(TicketMacoErrorConstants.EXPIRED_TICKET,
                    TicketMacoErrorConstants.PERMISSION_TICKET); //agrupa errores

    //inyeccion por constructor
    private final TicketMacoValidator macoValidator;

    @Inject
    public TracingRequestFilter(TicketMacoValidator macoValidator) {
        this.macoValidator = macoValidator;
    }

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {

        //LEEMOS CABECERA MACO
        String macoHeader = ctx.getHeaderString(MACO_HEADER);

        //validamos si no viene -> 400
        if (macoHeader == null || macoHeader.isBlank()) {
            abortOutcomeWithResponse(
                    ctx,
                    Response.Status.BAD_REQUEST,
                    "Solicitud incorrecta. El ticket debe venir informado",
                    List.of(HTTP_MACO),
                    HTTP_MACO
            );
            return;
        }

        /*
        * MACO no es null
        * validamos errores 403 y 401
         */
        String error = contentError(macoHeader);

        if(error != null){
             //control de status
            Response.Status status = FORBIDDEN_ERRORS.contains(error)
                    ? Response.Status.FORBIDDEN //403
                    : Response.Status.UNAUTHORIZED; //401

            //control de diagnostics
            String diagnostics = (status == Response.Status.FORBIDDEN)
                    ? "No autorizado. Token sin permiso o caducado"
                    : "No autorizado. Token invalido";

                 abortOutcomeWithResponse(
                         ctx,
                         status,
                         diagnostics,
                         List.of(HTTP_MACO),
                         HTTP_MACO
                 );
            return;
        }

        /*
        * MACO no contiene error
        * Validamos VERSION error 409
         */
        String versionHeader = ctx.getHeaderString(VERSION_HEADER);
        //rellenamos o capturamos ya que version es opcional en APIDOC
        String versionMaco = (versionHeader == null || versionHeader.isBlank())
                ? VERSION_SOPORTADA : versionHeader.trim();

            if(!VERSION_SOPORTADA.equals(versionMaco)){
                abortOutcomeWithResponse(
                        ctx,
                        Response.Status.CONFLICT,
                        "La versión del servicio no es soportada: " + versionMaco,
                        List.of(HTTP_VERSION),
                        HTTP_VERSION
                );
            }
    }
    /* ------------ Helpers ------------*/

    public String contentError(String firma) {
        String[] parts = firma.split(":");

        if (parts.length != 2) {
            return TicketMacoErrorConstants.MALFORMED_TICKET;
        }
        if(parts[0].isBlank() || parts[1].isBlank()){
            return TicketMacoErrorConstants.MALFORMED_TICKET;
        }
        return macoValidator.validate(parts[0],parts[1]);
    }

    //Operation+Response+abort
    private void abortOutcomeWithResponse(
                              ContainerRequestContext ctx,
                              Response.Status status,
                              String diagnostics,
                              List<String> expressions,
                              String text){

        OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                status.getStatusCode(),
                diagnostics,
                expressions,
                text
        );
        Response res = Response
                .status(status)
                .entity(operation) //obj
                .type(MediaType.APPLICATION_JSON)
                .build();

        ctx.abortWith(res);

    }



}
