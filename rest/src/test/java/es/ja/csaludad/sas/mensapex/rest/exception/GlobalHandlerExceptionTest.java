package es.ja.csaludad.sas.mensapex.rest.exception;

import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationError;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.hl7.fhir.r4.model.StringType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias de GlobalHandlerException")
class GlobalHandlerExceptionTest {

    private GlobalHandlerException handler;

    @Mock
    private UriInfo uriInfo;

    @Mock
    private Request request;

    @BeforeEach
    void setUp() {
        handler = new GlobalHandlerException();
        injectContextField("uriInfo", uriInfo);
        injectContextField("request", request);
    }

    @Test
    @DisplayName("Debe devolver 400 y construir expressions únicas desde ValidationError cuando ocurre ValidationBadRequestException")
    void shouldReturn400AndUniqueExpressionsWhenValidationBadRequestExceptionHasValidFields() {
        ValidationError error1 = buildValidationError("patientId");
        ValidationError error2 = buildValidationError("documentType");
        ValidationError error3 = buildValidationError("patientId");

        ValidationResult validationResult = buildValidationResult(List.of(error1, error2, error3));
        ValidationBadRequestException exception =
                buildValidationBadRequestException("Errores de validacion", validationResult);

        Response response = handler.toResponse(exception);

        assertEquals(400, response.getStatus());
        assertNotNull(response.getEntity());
        assertInstanceOf(OperationOutcome.class, response.getEntity());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Errores de validacion", issue.getDiagnostics());
        assertEquals("Solicitud incorrecta", extractIssueText(issue));

        List<String> expressions = extractExpressions(issue);
        assertTrue(expressions.contains("patientId"));
        assertTrue(expressions.contains("documentType"));
        assertEquals(2, expressions.size());
    }

    @Test
    @DisplayName("Debe devolver 400, diagnostics por defecto y expression system cuando ValidationBadRequestException tiene ValidationResult null")
    void shouldReturnSystemExpressionWhenValidationResultIsNull() {
        ValidationBadRequestException exception = mock(ValidationBadRequestException.class);
        when(exception.getValidationResult()).thenReturn(null);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(400, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Solicitud incorrecta", issue.getDiagnostics());
        assertEquals("Solicitud incorrecta", extractIssueText(issue));

        List<String> expressions = extractExpressions(issue);
        assertEquals(List.of("system"), expressions);
    }

    @Test
    @DisplayName("Debe devolver 400 y expression system cuando ValidationBadRequestException tiene lista de errores null")
    void shouldReturnSystemExpressionWhenValidationErrorsListIsNull() {
        ValidationResult validationResult = mock(ValidationResult.class);
        when(validationResult.getErrors()).thenReturn(null);

        ValidationBadRequestException exception = mock(ValidationBadRequestException.class);
        when(exception.getValidationResult()).thenReturn(validationResult);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(400, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Solicitud incorrecta", issue.getDiagnostics());
        assertEquals("Solicitud incorrecta", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 400 y expression system cuando los ValidationError no contienen fields válidos")
    void shouldReturnSystemExpressionWhenValidationFieldsAreInvalid() {
        ValidationError error1 = buildValidationError(null);
        ValidationError error2 = buildValidationError(" ");
        ValidationError error3 = buildValidationError("");

        ValidationResult validationResult = buildValidationResult(List.of(error1, error2, error3));
        ValidationBadRequestException exception =
                buildValidationBadRequestException(" ", validationResult);

        Response response = handler.toResponse(exception);

        assertEquals(400, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Solicitud incorrecta", issue.getDiagnostics());
        assertEquals("Solicitud incorrecta", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 400 para BadRequestException con diagnostics y text por defecto")
    void shouldReturn400WhenBadRequestException() {
        BadRequestException exception = mock(BadRequestException.class);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(400, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Solicitud incorrecta", issue.getDiagnostics());
        assertEquals("Solicitud incorrecta", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 401 para NotAuthorizedException con diagnostics y text por defecto")
    void shouldReturn401WhenNotAuthorizedException() {
        NotAuthorizedException exception = mock(NotAuthorizedException.class);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(401, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("No autorizado", issue.getDiagnostics());
        assertEquals("No autorizado", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 403 para ForbiddenException con diagnostics y text por defecto")
    void shouldReturn403WhenForbiddenException() {
        ForbiddenException exception = mock(ForbiddenException.class);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(403, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Prohibido", issue.getDiagnostics());
        assertEquals("Prohibido", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 404 y usar el path de UriInfo en expressions cuando ocurre NotFoundException")
    void shouldReturn404AndPathExpressionWhenNotFoundException() {
        NotFoundException exception = mock(NotFoundException.class);
        when(exception.getMessage()).thenReturn(" ");
        when(uriInfo.getPath()).thenReturn("gi-message/123");

        Response response = handler.toResponse(exception);

        assertEquals(404, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Recurso no encontrado", issue.getDiagnostics());
        assertEquals("Recurso no encontrado", extractIssueText(issue));
        assertEquals(List.of("/gi-message/123"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 404 y usar http.path cuando UriInfo devuelve path null")
    void shouldReturnHttpPathWhenNotFoundExceptionAndPathIsNull() {
        NotFoundException exception = mock(NotFoundException.class);
        when(exception.getMessage()).thenReturn(" ");
        when(uriInfo.getPath()).thenReturn(null);

        Response response = handler.toResponse(exception);

        assertEquals(404, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Recurso no encontrado", issue.getDiagnostics());
        assertEquals("Recurso no encontrado", extractIssueText(issue));
        assertEquals(List.of("http.path"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 404 y usar http.path cuando UriInfo devuelve path en blanco")
    void shouldReturnHttpPathWhenNotFoundExceptionAndPathIsBlank() {
        NotFoundException exception = mock(NotFoundException.class);
        when(exception.getMessage()).thenReturn(" ");
        when(uriInfo.getPath()).thenReturn("   ");

        Response response = handler.toResponse(exception);

        assertEquals(404, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Recurso no encontrado", issue.getDiagnostics());
        assertEquals("Recurso no encontrado", extractIssueText(issue));
        assertEquals(List.of("http.path"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 405, usar el método HTTP en diagnostics y el path en expressions cuando ocurre NotAllowedException")
    void shouldReturn405MethodDiagnosticsAndPathExpressionWhenNotAllowedException() {
        NotAllowedException exception = mock(NotAllowedException.class);
        when(request.getMethod()).thenReturn("POST");
        when(uriInfo.getPath()).thenReturn("gi-message");

        Response response = handler.toResponse(exception);

        assertEquals(405, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Método HTTP no permitido: POST", issue.getDiagnostics());
        assertEquals("Método no permitido", extractIssueText(issue));
        assertEquals(List.of("/gi-message"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 405 con diagnostics por defecto y expression http.path cuando Request y UriInfo son null")
    void shouldReturn405FallbackDiagnosticsAndHttpPathWhenRequestAndUriInfoAreNull() {
        NotAllowedException exception = mock(NotAllowedException.class);
        when(exception.getMessage()).thenReturn(" ");

        injectContextField("request", null);
        injectContextField("uriInfo", null);

        Response response = handler.toResponse(exception);

        assertEquals(405, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Método no permitido", issue.getDiagnostics());
        assertEquals("Método no permitido", extractIssueText(issue));
        assertEquals(List.of("http.path"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 422 para UnknownDocumentTypeException con diagnostics y text por defecto")
    void shouldReturn422WhenUnknownDocumentTypeException() {
        UnknownDocumentTypeException exception = mock(UnknownDocumentTypeException.class);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(422, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Entidad no procesable", issue.getDiagnostics());
        assertEquals("Entidad no procesable", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    @Test
    @DisplayName("Debe devolver 500 para una excepción genérica con diagnostics y text por defecto")
    void shouldReturn500WhenUnexpectedException() {
        Throwable exception = mock(Throwable.class);
        when(exception.getMessage()).thenReturn(" ");

        Response response = handler.toResponse(exception);

        assertEquals(500, response.getStatus());

        OperationOutcome.OperationOutcomeIssueComponent issue = extractIssue(response);

        assertEquals("Error interno no controlado", issue.getDiagnostics());
        assertEquals("Error interno", extractIssueText(issue));
        assertEquals(List.of("system"), extractExpressions(issue));
    }

    private ValidationError buildValidationError(String field) {
        ValidationError error = mock(ValidationError.class);
        when(error.getField()).thenReturn(field);
        return error;
    }

    private ValidationResult buildValidationResult(List<ValidationError> errors) {
        ValidationResult validationResult = mock(ValidationResult.class);
        when(validationResult.getErrors()).thenReturn(errors);
        return validationResult;
    }

    private ValidationBadRequestException buildValidationBadRequestException(
            String message,
            ValidationResult validationResult
    ) {
        ValidationBadRequestException exception = mock(ValidationBadRequestException.class);
        when(exception.getMessage()).thenReturn(message);
        when(exception.getValidationResult()).thenReturn(validationResult);
        return exception;
    }

    private OperationOutcome.OperationOutcomeIssueComponent extractIssue(Response response) {
        assertNotNull(response.getEntity());
        assertInstanceOf(OperationOutcome.class, response.getEntity());

        OperationOutcome outcome = (OperationOutcome) response.getEntity();
        assertFalse(outcome.getIssue().isEmpty());

        return outcome.getIssueFirstRep();
    }

    private List<String> extractExpressions(OperationOutcome.OperationOutcomeIssueComponent issue) {
        return issue.getExpression().stream()
                .map(StringType::getValue)
                .toList();
    }

    private String extractIssueText(OperationOutcome.OperationOutcomeIssueComponent issue) {
        assertNotNull(issue.getDetails());
        return issue.getDetails().getText();
    }

    private void injectContextField(String fieldName, Object value) {
        try {
            Field field = GlobalHandlerException.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(handler, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail("No se pudo inyectar el campo '" + fieldName + "' en GlobalHandlerException", e);
        }
    }
}