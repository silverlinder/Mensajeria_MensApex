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
    public static final String MACO_EXPRESSION = "http.maco";
    public static final String VERSION_EXPRESSION = "http.version";
    private static final Set<String> FORBIDDEN_ERRORS =
            Set.of(TicketMacoErrorConstants.EXPIRED_TICKET,
                    TicketMacoErrorConstants.PERMISSION_TICKET); //agrupa errores

    private final TicketMacoValidator macoValidator;

    @Inject
    public TracingRequestFilter(TicketMacoValidator macoValidator) {
        this.macoValidator = macoValidator;
    }

    @Override
    public void filter(ContainerRequestContext ctx) throws IOException {
        // Validar presencia Maco
        String macoHeader = ctx.getHeaderString(MACO_HEADER);
        if (macoHeader == null || macoHeader.isBlank()) {
            abortWithOutcome(
                    ctx,
                    Response.Status.BAD_REQUEST, //400
                    "Solicitud incorrecta. El ticket debe venir informado",
                    MACO_EXPRESSION);
            return;
        }

        // Validar contenido Maco .
        String error = getMacoValidationError(macoHeader);
        if(error != null){
             //control de status
            Response.Status status = FORBIDDEN_ERRORS.contains(error)
                    ? Response.Status.FORBIDDEN //403
                    : Response.Status.UNAUTHORIZED; //401
            String diagnostics = (status == Response.Status.FORBIDDEN)
                    ? "No autorizado. Token sin permiso o caducado"
                    : "No autorizado. Token invalido";
            abortWithOutcome(
                         ctx,
                         status,
                         diagnostics,
                         MACO_EXPRESSION);
            return;
        }

        // Maco ok -> Validar VERSION
        String versionHeader = normalizeVersion(ctx.getHeaderString(VERSION_HEADER));
            if(!VERSION_SOPORTADA.equals(versionHeader)){
                abortWithOutcome(
                        ctx,
                        Response.Status.CONFLICT, //409
                        "La version del servicio no es soportada: " + versionHeader,
                        VERSION_EXPRESSION);
            }
    }
    /* ------------ Helpers ------------*/
    private String normalizeVersion(String versionHeader) {
        if (versionHeader == null || versionHeader.isBlank()) {
            return VERSION_SOPORTADA;
        }
        return versionHeader.trim();
    }

    public String getMacoValidationError(String firma) {
        String[] parts = firma.split(":",-1);

        if (parts.length != 2 || parts[0].isBlank() || parts[1].isBlank()){
            return TicketMacoErrorConstants.MALFORMED_TICKET;
        }
        return macoValidator.validate(parts[0],parts[1]);
    }

    //Operation+Response+abort
    private void abortWithOutcome(
                              ContainerRequestContext ctx,
                              Response.Status status,
                              String diagnostics,
                              String expression){

        OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                status.getStatusCode(),
                diagnostics,
                List.of(expression),
                expression
        );
        Response res = Response
                .status(status)
                .entity(operation) //obj
                .type(MediaType.APPLICATION_JSON)
                .build();

        ctx.abortWith(res);

    }



}
