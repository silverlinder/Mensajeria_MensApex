package es.ja.csaludad.sas.mensapex.rest.entidad;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.usecases.GetEntidadUseCase;
import es.ja.csaludad.sas.mensapex.rest.entidad.criteria.EntidadCriteriaDTO;
import es.ja.csaludad.sas.mensapex.rest.entidad.dto.EntidadDTO;
import es.ja.csaludad.sas.mensapex.rest.entidad.mapper.EntidadCriteriaMapper;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Collection;

import static org.eclipse.microprofile.openapi.annotations.enums.SchemaType.ARRAY;

@Path("/entidades")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Recurso de entidades", description = "Gestión y consulta de entidades de ejemplo")
public class EntidadesResource {

    private final GetEntidadUseCase getEntidadUseCase;
    private final Mapper<EntidadDTO, Entidad> entidadDTOMapper;

    @Inject
    public EntidadesResource(GetEntidadUseCase getEntidadUseCase, Mapper<EntidadDTO, Entidad> entidadDTOMapper) {
        this.getEntidadUseCase = getEntidadUseCase;
        this.entidadDTOMapper = entidadDTOMapper;
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtiene una entidad por su identificador")
    @APIResponse(responseCode = "200",
            description = "Entidad encontrada",
            content = @Content(schema = @Schema(implementation = EntidadDTO.class)))
    public Response get(@PathParam("id") Long idEntidad) {
        Entidad entidad = getEntidadUseCase.get(idEntidad);

        EntidadDTO entidadDTO = entidadDTOMapper.mapReverse(entidad);

        return Response.ok(entidadDTO).build();
    }

    @GET
    @Operation(summary = "Lista entidades con filtros y ordenación")
    @APIResponse(responseCode = "200",
            description = "Listado de entidades",
            content = @Content(schema = @Schema(implementation = EntidadDTO.class, type = ARRAY)))
    public Response getAll(@BeanParam EntidadCriteriaDTO entidadCriteriaDTO) {
        EntidadCriteria entidadCriteria = EntidadCriteriaMapper.toDomain(entidadCriteriaDTO);

        Collection<Entidad> entidades = getEntidadUseCase.getAll(entidadCriteria);

        Collection<EntidadDTO> entidadDTOs = entidadDTOMapper.mapReverse(entidades);

        return Response.ok(entidadDTOs).build();
    }

    @GET
    @Path("/authenticated")
    @Operation(summary = "Prueba de autenticación")
    @APIResponse(responseCode = "200", description = "Endpoint autenticado para pruebas")
    @RolesAllowed({"view-resource", "manage-resource"})
    public Response authenticated() {
        return Response.ok("Authenticated user").build();
    }

}
