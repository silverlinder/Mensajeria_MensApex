package es.ja.csaludad.sas.mensapex.rest.mapper;

import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import org.hl7.fhir.r4.model.Bundle;

import java.util.function.Function;

public class BundleToDocumentPayloadDTOMapper implements Function<Bundle, DocumentPayloadDTO> {
    @Override
    public DocumentPayloadDTO apply(Bundle bundle) {
        //TODO:Implementar Mapeo para la validacion
        return null;
    }
}
