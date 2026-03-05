package es.ja.csaludad.sas.mensapex.rest.exception.mapper;

import es.ja.csaludad.sas.mensapex.rest.exception.UnknownDocumentTypeException;
import es.ja.csaludad.sas.mensapex.rest.exception.ValidationBadRequestException;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;
//TODO: Mappear el OperationOutcome de Error
@Provider
@ApplicationScoped
public class BadRequestExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException ex) {

        // Caso: tipo desconocido => 400
        if (ex instanceof UnknownDocumentTypeException) {
            Map<String, Object> body = new HashMap<>();
            body.put("message", ex.getMessage());
            body.put("errors", new Object[0]);
            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(body)
                    .build();
        }

        // Caso: validación => 400 con detalle
        if (ex instanceof ValidationBadRequestException v) {
            ValidationResult vr = v.getValidationResult();

            Map<String, Object> body = new HashMap<>();
            body.put("message", v.getMessage());
            body.put("errors", vr.getErrors());

            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(body)
                    .build();
        }

        // No lo manejamos aquí
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of("message", "Error interno"))
                .build();
    }
}