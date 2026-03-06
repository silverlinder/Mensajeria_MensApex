package es.ja.csaludad.sas.mensapex.rest.validator.service;

import es.ja.csaludad.sas.mensapex.rest.exception.ValidationBadRequestException;
import es.ja.csaludad.sas.mensapex.rest.validator.DocumentValidator;
import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import es.ja.csaludad.sas.mensapex.rest.validator.entity.ValidationResult;
import es.ja.csaludad.sas.mensapex.rest.validator.factory.DocumentValidatorFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;

@ApplicationScoped
public class ValidationService {

    @Inject
    DocumentValidatorFactory factory;

    public void validateOrThrow(DocumentPayloadDTO payload) {
        DocumentValidator validator = factory.getValidator(payload.getDocumentType());
        ValidationResult result = validator.validate(payload); //SERGIO le aplicamos las reglas del validador

        if (!result.isValid()) {
            // Lanzamos BadRequest con el detalle para que lo transforme el ExceptionMapper
            throw new ValidationBadRequestException("Documento inválido", result);
        }
    }
}