package es.ja.csaludad.sas.mensapex.rest.interceptor;

import es.ja.csalud.sas.componentescomunes.macoapiclient.ticket.api.control.TicketValidation;
import es.ja.csaludad.sas.mensapex.adapter.maco.internal.TicketMacoValidator;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TracingRequestFilterTest {
    @Mock
    private TicketValidation macoValidator;
    @Mock
    private ContainerRequestContext ctx;

    private TracingRequestFilter filter; //clase real
    @BeforeEach
    void setUp(){
        filter = new TracingRequestFilter((TicketMacoValidator) macoValidator);//casting??
    }

    @Test
    @DisplayName("Devolver 400 cuando no hay cabecera maco")
    void testAbortWithMacoHeaderIsMissing(){
        when(ctx.getHeaderString(TracingRequestFilter.MACO_HEADER)).thenReturn(null);

        assertDoesNotThrow(() -> filter.filter(ctx));

        verify(ctx).abortWith(any(Response.class)); // si no llega al controller
        verifyNoInteractions(macoValidator); // si no llega al validador
    }

   //working hard ...
}
