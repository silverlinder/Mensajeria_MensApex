package es.ja.csaludad.sas.mensapex.rest.entidad.exception;

import es.ja.csalud.sas.framework.domain.exception.BusinessException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.MediaType;

/**
 * Ver https://ws001.sspa.juntadeandalucia.es/confluence/x/v_UxDQ
 */
@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException exception) {
        String message = exception.getMessage();

        if (message != null && message.startsWith("ERRROR-001")) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Entidad no encontrada", "ERRROR-001"))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(message, "UNKNOWN"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    public record ErrorResponse(String message, String code) {
    }
}
