package es.ja.csaludad.sas.mensapex.rest.interceptor;

import es.ja.csaludad.sas.mensapex.adapter.maco.internal.TicketMacoValidator;
import es.ja.csaludad.sas.mensapex.rest.interceptor.enums.TicketMacoErrorConstants;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.hl7.fhir.r4.model.OperationOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TracingRequestFilterTest {

    @Mock
    private TicketMacoValidator macoValidator;

    @Mock
    private ContainerRequestContext ctx;

    private TracingRequestFilter filter;

    @BeforeEach
    void setUp() {
        filter = new TracingRequestFilter(macoValidator);
    }
    //400 por maco nulo
    @Test
    @DisplayName("400 cuando falta la cabecera maco")
    void shouldAbortWithBadRequestWhenMacoHeaderIsNull() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn(null);

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 400,
                "Solicitud incorrecta. El ticket debe venir informado",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //400 por maco en blanco
    @Test
    @DisplayName("400 cuando la cabecera maco viene en blanco")
    void shouldAbortWithBadRequestWhenMacoHeaderIsBlank() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("   ");

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 400,
                "Solicitud incorrecta. El ticket debe venir informado",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //401 por ticket malformado sin separador
    @Test
    @DisplayName("401 cuando el ticket MACO es malformado porque no tiene separador")
    void shouldAbortWithUnauthorizedWhenTicketHasNoSeparator() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("ticketSinSeparador");

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 401,
                "No autorizado. Token invalido",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //401 por parte izquierda vacia
    @Test
    @DisplayName("401 cuando el ticket MACO es malformado porque el usuario esta vacio")
    void shouldAbortWithUnauthorizedWhenTicketUserPartIsBlank() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn(" :firma");

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 401,
                "No autorizado. Token invalido",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //401 por firma invalida
    @Test
    @DisplayName("401 cuando el validador devuelve firma invalida")
    void shouldAbortWithUnauthorizedWhenValidatorReturnsInvalidSignature() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma"))
                .thenReturn(TicketMacoErrorConstants.SIGNATURE_TICKET);

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 401,
                "No autorizado. Token invalido",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //403 por ticket caducado
    @Test
    @DisplayName("403 cuando el ticket esta caducado")
    void shouldAbortWithForbiddenWhenTicketIsExpired() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma"))
                .thenReturn(TicketMacoErrorConstants.EXPIRED_TICKET);

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 403,
                "No autorizado. Token sin permiso o caducado",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //403 por falta de permisos
    @Test
    @DisplayName("403 cuando el ticket no tiene permisos suficientes")
    void shouldAbortWithForbiddenWhenTicketHasNoPermissions() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma"))
                .thenReturn(TicketMacoErrorConstants.PERMISSION_TICKET);

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 403,
                "No autorizado. Token sin permiso o caducado",
                TracingRequestFilter.HTTP_MACO,
                TracingRequestFilter.HTTP_MACO);
    }
    //409 por version no soportada
    @Test
    @DisplayName("409 cuando la version no esta soportada")
    void shouldAbortWithConflictWhenVersionIsNotSupported() throws Exception {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma")).thenReturn(null);
        when(ctx.getHeaderString(TracingRequestFilter.VERSION_HEADER)).thenReturn("v2.0");

        filter.filter(ctx);

        Response response = captureAbortResponse();
        assertResponse(response, 409,
                "La version del servicio no es soportada: v2.0",
                TracingRequestFilter.HTTP_VERSION,
                TracingRequestFilter.HTTP_VERSION);
    }
    //caso valido
    @Test
    @DisplayName("No aborta cuando el ticket es valido y la version no viene informada")
    void shouldNotAbortWhenTicketIsValidAndVersionIsMissing() {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma")).thenReturn(null);
        when(ctx.getHeaderString(TracingRequestFilter.VERSION_HEADER)).thenReturn(null);

        assertDoesNotThrow(() -> filter.filter(ctx));

        verify(ctx, never()).abortWith(any());
    }
    // valido con version
    @Test
    @DisplayName("No aborta cuando el ticket es valido y la version soportada viene informada")
    void shouldNotAbortWhenTicketIsValidAndVersionIsSupported() {
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn("usuario:firma");
        when(macoValidator.validate("usuario", "firma")).thenReturn(null);
        when(ctx.getHeaderString(TracingRequestFilter.VERSION_HEADER))
                .thenReturn(TracingRequestFilter.VERSION_SOPORTADA);

        assertDoesNotThrow(() -> filter.filter(ctx));

        verify(ctx, never()).abortWith(any());
    }
    // metodo contentError ticket con 3 partes
    @Test
    @DisplayName("contentError devuelve Ticket malformed cuando el formato tiene mas de un separador")
    void contentErrorShouldReturnMalformedWhenTicketHasMoreThanOneSeparator() {
        String result = filter.contentError("usuario:parte1:parte2");

        assertEquals(TicketMacoErrorConstants.MALFORMED_TICKET, result);
    }

    @Test
    @DisplayName("contentError delega en TicketMacoValidator cuando el ticket tiene formato correcto")
    void contentErrorShouldDelegateToValidatorWhenFormatIsCorrect() {
        when(macoValidator.validate("usuario", "firma"))
                .thenReturn(TicketMacoErrorConstants.FUTURE_TICKET);

        String result = filter.contentError("usuario:firma");

        assertEquals(TicketMacoErrorConstants.FUTURE_TICKET, result);
        verify(macoValidator).validate(eq("usuario"), eq("firma"));
    }

    /* ----------Helpers -------------------*/

    private Response captureAbortResponse() {
        ArgumentCaptor<Response> captor = ArgumentCaptor.forClass(Response.class);
        verify(ctx).abortWith(captor.capture());
        return captor.getValue();
    }

    private void assertResponse(Response response,
                                int expectedStatus,
                                String expectedDiagnostics,
                                String expectedExpression,
                                String expectedDetailsText) {
        assertNotNull(response);
        assertEquals(expectedStatus, response.getStatus());

        OperationOutcome outcome = (OperationOutcome) response.getEntity();
        assertNotNull(outcome);
        assertEquals(1, outcome.getIssue().size());
        assertEquals(expectedDiagnostics, outcome.getIssueFirstRep().getDiagnostics());
        assertEquals(expectedDetailsText, outcome.getIssueFirstRep().getDetails().getText());
        assertEquals(expectedExpression, outcome.getIssueFirstRep().getExpression().getFirst().primitiveValue());
    }
}
