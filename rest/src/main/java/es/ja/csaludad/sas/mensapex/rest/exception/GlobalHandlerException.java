package es.ja.csaludad.sas.mensapex.rest.exception;
/*
* Responsabilidad: actua cuando ya se ha producido una excepción en controller, servicio, validación, mapper
* transforma esa excepción en una respuesta de tipo Outcome FHIR*/

import es.ja.csaludad.sas.mensapex.rest.factory.OperationOutcomeFactory;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationError;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hl7.fhir.r4.model.OperationOutcome;

import java.util.List;
import java.util.Objects;

@Provider
public class GlobalHandlerException implements ExceptionMapper<Throwable> {
    private static final int UNPROCESSABLE_ENTITY_STATUS = 422;
    private static final String SYSTEM_EXPRESSION = "system";
    private static final String HTTP_PATH_EXPRESSION = "http.path";

    @Context
    private UriInfo uriInfo; //ruta
    @Context
    private Request request;

    @Override
    public Response toResponse(Throwable exception) {
        int status = createStatus(exception); //400, 404, 500 ...
        String diagnostics = createDiagnostics(exception, status);
        List<String> expressions = createExpressions(exception, status);
        String text = createText(status);

        //contrato FHIR
        OperationOutcome operation = OperationOutcomeFactory.fromHttpStatus(
                status,
                diagnostics,
                expressions,
                text
        );

        return Response
                .status(status)
                .entity(operation)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

/* -------------------  Helpers   -------------- */
    private int createStatus(Throwable exception) {
        //validamos
        if (exception instanceof ValidationBadRequestException) {
            return Response.Status.BAD_REQUEST.getStatusCode();
        }
        if (exception instanceof BadRequestException) {
            return Response.Status.BAD_REQUEST.getStatusCode();
        }
        if (exception instanceof NotAuthorizedException) {
            return Response.Status.UNAUTHORIZED.getStatusCode();
        }
        if (exception instanceof ForbiddenException) {
            return Response.Status.FORBIDDEN.getStatusCode();
        }
        if (exception instanceof NotFoundException) {
            return Response.Status.NOT_FOUND.getStatusCode();
        }
        if (exception instanceof NotAllowedException) {
            return Response.Status.METHOD_NOT_ALLOWED.getStatusCode();
        }
        if (exception instanceof UnknownDocumentTypeException) {
            return UNPROCESSABLE_ENTITY_STATUS;
        }
        return Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    }

    private String createDiagnostics(Throwable exception, int status) {
        //si es 405
        if (status == Response.Status.METHOD_NOT_ALLOWED.getStatusCode() && request != null) {
            return "Método HTTP no permitido: " + request.getMethod(); //POST, GET...
        }
        //si existe mensaje
        if (exception.getMessage() != null && !exception.getMessage().isBlank()) {
            return exception.getMessage();
        }
        return switch (status) {
            case 400 -> "Solicitud incorrecta";
            case 401 -> "No autorizado";
            case 403 -> "Prohibido";
            case 404 -> "Recurso no encontrado";
            case 405 -> "Método no permitido";
            case 422 -> "Entidad no procesable";
            default -> "Error interno no controlado";
        };
    }

    private List<String> createExpressions(Throwable exception, int status) {
        //devolvemos lista
        if (exception instanceof ValidationBadRequestException validationException
                && validationException.getValidationResult() != null
                && validationException.getValidationResult().getErrors() != null) {
            List<String> fields = validationException.getValidationResult().getErrors().stream()
                    .map(ValidationError::getField)
                    .filter(Objects::nonNull)
                    .filter(field -> !field.isBlank())
                    .distinct()
                    .toList();
            if(!fields.isEmpty()){
                return fields; // -> ["patientId", "documentType"]...
            }
        }
        //404,405 -> devolvemos path
        if(status == Response.Status.NOT_FOUND.getStatusCode() || status == Response.Status.METHOD_NOT_ALLOWED.getStatusCode()){
            return List.of(extractRequestPath()); // /gi-message/123 ...
        }
        return List.of(SYSTEM_EXPRESSION);
    }

    private String createText(int status) {
        return switch (status) {
            case 400 -> "Solicitud incorrecta";
            case 401 -> "No autorizado";
            case 403 -> "Prohibido";
            case 404 -> "Recurso no encontrado";
            case 405 -> "Método no permitido";
            case 422 -> "Entidad no procesable";
            default -> "Error interno";
        };
    }

    private String extractRequestPath() {
        if (uriInfo == null || uriInfo.getPath() == null || uriInfo.getPath().isBlank()) {
            return HTTP_PATH_EXPRESSION; //evitamos null
        }
        return "/" + uriInfo.getPath();
    }
}
