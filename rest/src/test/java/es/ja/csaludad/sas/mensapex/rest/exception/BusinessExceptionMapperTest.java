package es.ja.csaludad.sas.mensapex.rest.exception;

import es.ja.csalud.sas.framework.domain.exception.BusinessException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BusinessExceptionMapperTest {

    private final BusinessExceptionMapper mapper = new BusinessExceptionMapper();

    @Test
    @DisplayName("ERRROR-001 -> 404 NOT_FOUND con payload estándar")
    void mapsErr001ToNotFound() {
        BusinessException ex = new BusinessException("ERRROR-001: Entidad no encontrada: 7");

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

        BusinessExceptionMapper.ErrorResponse body =
                (BusinessExceptionMapper.ErrorResponse) response.getEntity();

        assertNotNull(body);
        assertEquals("ERRROR-001", body.code());
        assertEquals("Entidad no encontrada", body.message());
    }

    @Test
    @DisplayName("Mensaje distinto -> 500 INTERNAL_SERVER_ERROR con code=UNKNOWN y mensaje original")
    void mapsOtherErrorToInternalServerError() {
        BusinessException ex = new BusinessException("Algo fue mal");

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

        BusinessExceptionMapper.ErrorResponse body =
                (BusinessExceptionMapper.ErrorResponse) response.getEntity();

        assertNotNull(body);
        assertEquals("UNKNOWN", body.code());
        assertEquals("Algo fue mal", body.message());
    }

    @Test
    @DisplayName("Mensaje vacío -> 500 INTERNAL_SERVER_ERROR con code=UNKNOWN y message=\"\"")
    void mapsEmptyMessageToInternalServerError() {
        BusinessException ex = new BusinessException("");

        Response response = mapper.toResponse(ex);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

        BusinessExceptionMapper.ErrorResponse body =
                (BusinessExceptionMapper.ErrorResponse) response.getEntity();

        assertNotNull(body);
        assertEquals("UNKNOWN", body.code());
        assertEquals("", body.message());
    }
}
