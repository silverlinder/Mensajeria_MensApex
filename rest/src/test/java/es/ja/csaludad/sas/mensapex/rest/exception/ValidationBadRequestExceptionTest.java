package es.ja.csaludad.sas.mensapex.rest.exception;

import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pruebas unitarias de ValidationBadRequestException")
class ValidationBadRequestExceptionTest {

    private static final String ERROR_MESSAGE = "Errores de validacion";

    @Mock
    private ValidationResult validationResult;

    private ValidationBadRequestException exception;

    @BeforeEach
    void setUp() {
        exception = new ValidationBadRequestException(ERROR_MESSAGE, validationResult);
    }

    @Test
    @DisplayName("Debe extender de BadRequestException")
    void shouldExtendBadRequestException() {
        assertInstanceOf(BadRequestException.class, exception);
    }

    @Test
    @DisplayName("Debe conservar el mensaje recibido en el constructor")
    void shouldKeepConstructorMessage() {
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Debe devolver el ValidationResult recibido en el constructor")
    void shouldReturnValidationResultFromGetter() {
        assertSame(validationResult, exception.getValidationResult());
    }

    @Test
    @DisplayName("Debe permitir ValidationResult null y devolverlo en el getter")
    void shouldAllowNullValidationResult() {
        ValidationBadRequestException nullValidationResultException =
                new ValidationBadRequestException(ERROR_MESSAGE, null);

        assertEquals(ERROR_MESSAGE, nullValidationResultException.getMessage());
        assertNull(nullValidationResultException.getValidationResult());
    }

    @Test
    @DisplayName("Debe permitir mensaje null y conservar el ValidationResult")
    void shouldAllowNullMessage() {
        ValidationBadRequestException nullMessageException =
                new ValidationBadRequestException(null, validationResult);

        assertNull(nullMessageException.getMessage());
        assertSame(validationResult, nullMessageException.getValidationResult());
    }
}