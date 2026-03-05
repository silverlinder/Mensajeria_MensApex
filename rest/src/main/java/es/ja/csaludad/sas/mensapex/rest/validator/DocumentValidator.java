package es.ja.csaludad.sas.mensapex.rest.validator;

import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;

public interface DocumentValidator {
    /**
     * Tipo de documento que soporta la estrategia.
     * Ej: "NuevoDocumento", "modificacionDocumento"
     */
    String supportsType();

    ValidationResult validate(DocumentPayloadDTO payload);
}