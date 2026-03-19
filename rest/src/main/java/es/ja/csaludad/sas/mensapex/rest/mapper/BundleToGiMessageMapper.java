package es.ja.csaludad.sas.mensapex.rest.mapper;

import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
import org.hl7.fhir.r4.model.Bundle;

import java.util.function.Function;

public class BundleToGiMessageMapper implements Function<Bundle, GiMessage> {
    @Override
    public GiMessage apply(Bundle bundle) {
        //TODO: Ver que campos vamos a Mapear al GiMessage y montarlo con el patron builder
        return null;
    }
}
