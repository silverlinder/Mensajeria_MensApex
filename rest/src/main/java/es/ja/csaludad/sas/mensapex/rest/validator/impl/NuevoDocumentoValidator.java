package es.ja.csaludad.sas.mensapex.rest.validator.impl;

import es.ja.csaludad.sas.mensapex.rest.validator.DocumentValidator;
import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import jakarta.enterprise.context.ApplicationScoped;

//TODO: ver que campos vamos a validar
@ApplicationScoped
public class NuevoDocumentoValidator implements DocumentValidator {

    public static final String REQUIRED = "REQUIRED";

    @Override
    public String supportsType() {
        return "NuevoDocumento";
    }
    //reglas de validacion
    @Override
    public ValidationResult validate(DocumentPayloadDTO payload) {
        ValidationResult result = ValidationResult.ok();

        // Campos requeridos: NUHSA, VERSION, MODULE, PROTOCOLO, SEGUIMIENTO CLINICO
        if (isBlank(payload.getNuhsa())) {
            result.addError("nuhsa", REQUIRED, "NUHSA es obligatorio");
        }
        if (isBlank(payload.getVersion())) {
            result.addError("version", REQUIRED, "VERSION es obligatoria");
        }
        if (isBlank(payload.getModule())) {
            result.addError("module", REQUIRED, "MODULE es obligatorio");
        }
        if (isBlank(payload.getProtocolo())) {
            result.addError("protocolo", REQUIRED, "PROTOCOLO es obligatorio");
        }
        if (isBlank(payload.getSeguimientoClinico())) {
            result.addError("seguimientoClinico", REQUIRED, "SEGUIMIENTO CLINICO es obligatorio");
        }

        // Reglas de negocio específicas (ejemplo)
        if (!isBlank(payload.getVersion()) && !payload.getVersion().matches("\\d+(\\.\\d+)*")) {
            result.addError("version", "INVALID_FORMAT", "VERSION debe tener formato numérico (ej: 1 o 1.0)");
        }

        return result;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}