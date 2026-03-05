package es.ja.csaludad.sas.mensapex.rest.exception;

import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.ws.rs.BadRequestException;

public class ValidationBadRequestException extends BadRequestException {

    private final ValidationResult validationResult;

    public ValidationBadRequestException(String message, ValidationResult validationResult) {
        super(message);
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }
}