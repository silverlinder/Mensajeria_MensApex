package es.ja.csaludad.sas.mensapex.rest.interceptor;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;

import java.io.IOException;

public class TracingRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

    }
}
