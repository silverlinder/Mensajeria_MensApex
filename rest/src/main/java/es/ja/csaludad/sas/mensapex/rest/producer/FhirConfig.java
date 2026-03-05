package es.ja.csaludad.sas.mensapex.rest.producer;

import ca.uhn.fhir.context.FhirContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class FhirConfig {

    @Produces
    @ApplicationScoped
    public FhirContext fhirR4Context() {
        return FhirContext.forR4();
    }
}
