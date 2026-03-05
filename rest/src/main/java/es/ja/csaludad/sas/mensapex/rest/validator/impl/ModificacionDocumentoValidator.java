package es.ja.csaludad.sas.mensapex.rest.validator.impl;

import es.ja.csaludad.sas.mensapex.rest.validator.DocumentValidator;
import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.enterprise.context.ApplicationScoped;

//TODO: Ver por que campos validar
@ApplicationScoped
public class ModificacionDocumentoValidator implements DocumentValidator {

    public static final String REQUIRED = "REQUIRED";

    @Override
    public String supportsType() {
        return "modificacionDocumento";
    }

    @Override
    public ValidationResult validate(DocumentPayloadDTO payload) {
        ValidationResult result = ValidationResult.ok();

        if (payload.getNuhsa() == null || payload.getNuhsa().trim().isEmpty()) {
            result.addError("nuhsa", REQUIRED, "NUHSA es obligatorio para TipoX");
        }
        if (payload.getProtocolo() == null || payload.getProtocolo().trim().isEmpty()) {
            result.addError("protocolo", REQUIRED, "PROTOCOLO es obligatorio para TipoX");
        }

        return result;
    }
}