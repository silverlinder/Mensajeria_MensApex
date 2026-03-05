package es.ja.csaludad.sas.mensapex.rest.provider;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Bundle;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyReader;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Provider
@ApplicationScoped
@Consumes({ "application/fhir+json", "application/json" })
@Produces({ "application/fhir+json", "application/json" })
public class BundleJsonProvider implements MessageBodyReader<Bundle>, MessageBodyWriter<Bundle> {

    @Inject
    FhirContext fhirContext;

    // ---------- READER ----------

    @Override
    public boolean isReadable(Class<?> type, Type genericType,
                              Annotation[] annotations, MediaType mediaType) {
        return Bundle.class.isAssignableFrom(type)
                && isJsonFhir(mediaType);
    }

    @Override
    public Bundle readFrom(Class<Bundle> type,
                           Type genericType,
                           Annotation[] annotations,
                           MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {

        IParser parser = fhirContext.newJsonParser();
        parser.setStripVersionsFromReferences(false);
        parser.setOverrideResourceIdWithBundleEntryFullUrl(false);

        try (InputStreamReader reader =
                     new InputStreamReader(entityStream, StandardCharsets.UTF_8)) {
            return parser.parseResource(Bundle.class, reader);
        }
    }

    // ---------- WRITER ----------

    @Override
    public boolean isWriteable(Class<?> type, Type genericType,
                               Annotation[] annotations, MediaType mediaType) {
        return Bundle.class.isAssignableFrom(type)
                && isJsonFhir(mediaType);
    }

    @Override
    public void writeTo(Bundle bundle,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {

        IParser parser = fhirContext.newJsonParser();
        parser.setPrettyPrint(true);

        try (OutputStreamWriter writer =
                     new OutputStreamWriter(entityStream, StandardCharsets.UTF_8)) {
            parser.encodeResourceToWriter(bundle, writer);
        }
    }

    @Override
    public long getSize(Bundle bundle,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType) {
        // Deprecated en JAX-RS 2.0, se ignora normalmente
        return -1;
    }

    // ---------- Helpers ----------

    private boolean isJsonFhir(MediaType mediaType) {
        if (mediaType == null) {
            return false;
        }
        String type = mediaType.toString();
        return type.equalsIgnoreCase("application/fhir+json")
                || type.equalsIgnoreCase(MediaType.APPLICATION_JSON);
    }
}
