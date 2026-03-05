package es.ja.csaludad.sas.mensapex.rest.validator.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationResult {

    private final List<ValidationError> errors = new ArrayList<>();

    public static ValidationResult ok() {
        return new ValidationResult();
    }

    public static ValidationResult withError(String field, String code, String message) {
        ValidationResult r = new ValidationResult();
        r.addError(field, code, message);
        return r;
    }

    public void addError(String field, String code, String message) {
        errors.add(new ValidationError(field, code, message));
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<ValidationError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}