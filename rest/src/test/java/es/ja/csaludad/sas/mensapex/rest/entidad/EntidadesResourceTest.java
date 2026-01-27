package es.ja.csaludad.sas.mensapex.rest.entidad;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.usecases.GetEntidadUseCase;
import es.ja.csaludad.sas.mensapex.rest.entidad.criteria.EntidadCriteriaDTO;
import es.ja.csaludad.sas.mensapex.rest.entidad.dto.EntidadDTO;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.RuntimeDelegate;
import org.glassfish.jersey.internal.RuntimeDelegateImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EntidadesResourceTest {

    @BeforeAll
    static void initRuntimeDelegate() {
        RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
    }

    @Test
    @DisplayName("GET /entidad/{id} -> 200 y cuerpo DTO")
    void getById_ok() {
        GetEntidadUseCase useCase = mock(GetEntidadUseCase.class);
        @SuppressWarnings("unchecked")
        Mapper<EntidadDTO, Entidad> mapper = (Mapper<EntidadDTO, Entidad>) mock(Mapper.class);

        EntidadesResource resource = new EntidadesResource(useCase, mapper);

        Entidad entidad = new Entidad.Builder()
                .withId(7L).withNombre("Hosp").withDescripcion("Desc").withFechaCreacion(LocalDateTime.now())
                .build();
        EntidadDTO dto = new EntidadDTO();
        dto.setId(7L);
        dto.setNombre("Hosp");
        dto.setDescripcion("Desc");

        when(useCase.get(7L)).thenReturn(entidad);
        when(mapper.mapReverse(entidad)).thenReturn(dto);

        Response resp = resource.get(7L);

        assertEquals(200, resp.getStatus());
        assertSame(dto, resp.getEntity());
        verify(useCase).get(7L);
        verify(mapper).mapReverse(entidad);
    }

    @Test
    @DisplayName("GET /entidad -> 200 y lista de DTOs")
    void getAll_ok() {
        GetEntidadUseCase useCase = mock(GetEntidadUseCase.class);
        @SuppressWarnings("unchecked")
        Mapper<EntidadDTO, Entidad> mapper = (Mapper<EntidadDTO, Entidad>) mock(Mapper.class);

        EntidadesResource resource = new EntidadesResource(useCase, mapper);

        EntidadCriteriaDTO criteriaDTO = new EntidadCriteriaDTO();
        criteriaDTO.setNombre("Juan");
        criteriaDTO.setDescripcion("Cardio");
        criteriaDTO.setSort(new java.util.ArrayList<>(List.of("nombre,asc", "descripcion,desc")));

        Entidad e1 = new Entidad.Builder().withId(1L).withNombre("A").build();
        Entidad e2 = new Entidad.Builder().withId(2L).withNombre("B").build();
        Collection<Entidad> entidades = List.of(e1, e2);

        EntidadDTO d1 = new EntidadDTO();
        d1.setId(1L);
        d1.setNombre("A");
        EntidadDTO d2 = new EntidadDTO();
        d2.setId(2L);
        d2.setNombre("B");
        Collection<EntidadDTO> dtos = List.of(d1, d2);

        when(useCase.getAll(any())).thenReturn(entidades);
        when(mapper.mapReverse(entidades)).thenReturn(dtos);

        Response resp = resource.getAll(criteriaDTO);

        assertEquals(200, resp.getStatus());
        assertSame(dtos, resp.getEntity());
        verify(useCase).getAll(any());
        verify(mapper).mapReverse(entidades);
    }
}
