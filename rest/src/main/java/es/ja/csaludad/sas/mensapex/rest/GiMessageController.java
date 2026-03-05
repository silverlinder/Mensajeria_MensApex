package es.ja.csaludad.sas.mensapex.rest;

import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
import es.ja.csaludad.sas.mensapex.domain.entity.usecases.PostGiMessageUseCase;
import es.ja.csaludad.sas.mensapex.rest.mapper.BundleToDocumentPayloadDTOMapper;
import es.ja.csaludad.sas.mensapex.rest.mapper.BundleToGiMessageMapper;
import es.ja.csaludad.sas.mensapex.rest.validator.dto.DocumentPayloadDTO;
import es.ja.csaludad.sas.mensapex.rest.validator.service.ValidationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hl7.fhir.r4.model.Bundle;

@Path("/eventos/nuevoDocumento")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Recurso de entidades", description = "Gestión y consulta de entidades de ejemplo")
public class GiMessageController {

    private final PostGiMessageUseCase postGiMessageUseCase;
    private final BundleToGiMessageMapper bundleToGiMessageMapper;
    private final ValidationService validationService;
    private final BundleToDocumentPayloadDTOMapper bundleToDocumentPayloadDTOMapper;

    @Inject
    public GiMessageController(PostGiMessageUseCase postGiMessageUseCase, ValidationService validationService) {
        this.postGiMessageUseCase = postGiMessageUseCase;
        this.validationService = validationService;
        this.bundleToDocumentPayloadDTOMapper = new BundleToDocumentPayloadDTOMapper();
        this.bundleToGiMessageMapper = new BundleToGiMessageMapper();
    }

    @POST
    @Operation(summary = "Valida y Persiste un informe")
    @APIResponse(responseCode = "200", description = "Informe Persistido")
    public Response post(@RequestBody Bundle bundle){

        //Mapear al payload para validar los campos con el factory con patron Strategy
        DocumentPayloadDTO dto = bundleToDocumentPayloadDTOMapper.apply(bundle);
        //Validar mensaje segun el la factory con strategy
        validationService.validateOrThrow(dto);
        //Mapear del bundle a GiMessage
        GiMessage mapped = bundleToGiMessageMapper.apply(bundle);
        //Insertar GiMessage
        postGiMessageUseCase.insert(mapped);

        //Mappear Bundle de respuestas segun APIDOC
        Bundle bundleReponse = new Bundle();
        return Response.status(Response.Status.OK).entity(bundleReponse).build();
    }
}
