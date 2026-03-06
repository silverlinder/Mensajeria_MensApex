package es.ja.csaludad.sas.mensapex.rest.validator.factory;

import es.ja.csaludad.sas.mensapex.rest.exception.UnknownDocumentTypeException;
import es.ja.csaludad.sas.mensapex.rest.validator.DocumentValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DocumentValidatorFactory {

    private final Map<String, DocumentValidator> byType = new HashMap<>();
    //SERGIO inyecta los BEANS implementados de la (I) DocumentValidator -> array de validadores
    @Inject
    public DocumentValidatorFactory(Instance<DocumentValidator> validators) {
        for (DocumentValidator v : validators) {
            byType.put(normalize(v.supportsType()), v);
            //SERGIO guarda todos los type creados de las clases que hayan implementado la (I)
        }
    }
    //SERGIO recibe type -> NuevoDocumento
    public DocumentValidator getValidator(String documentType) {
        if (documentType == null || documentType.trim().isEmpty()) {
            throw new UnknownDocumentTypeException("documentType es obligatorio");
        }

        DocumentValidator v = byType.get(normalize(documentType));
        if (v == null) {
            throw new UnknownDocumentTypeException("Tipo de documento no soportado: " + documentType);
        }
        return v; //SERGIO devuelve el validador
    }

    private String normalize(String s) {
        return s.trim().toLowerCase();
    }
}