package es.ja.csaludad.sas.mensapex.rest.entidad.mapper;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.rest.entidad.dto.EntidadDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

class EntidadDTOMapperTest {

    private final EntidadDTOMapper mapper = new EntidadDTOMapper();

    @Test
    @DisplayName("mapReverse(Entidad) crea DTO con los campos correctos")
    void mapReverse_createsDto() {
        LocalDateTime now = LocalDateTime.now();
        Entidad entidad = new Entidad.Builder()
                .withId(11L)
                .withNombre("Hospital")
                .withDescripcion("Desc")
                .withFechaCreacion(now)
                .build();

        EntidadDTO dto = mapper.mapReverse(entidad);

        assertNotNull(dto);
        assertEquals(11L, dto.getId());
        assertEquals("Hospital", dto.getNombre());
        assertEquals("Desc", dto.getDescripcion());
        assertEquals(now, dto.getFechaCreacion());
    }

    @Test
    @DisplayName("mapReverse(Entidad, EntidadDTO) rellena el DTO proporcionado y lo devuelve")
    void mapReverse_overload_updatesProvidedDto() {
        LocalDateTime now = LocalDateTime.now();
        Entidad entidad = new Entidad.Builder()
                .withId(2L).withNombre("A").withDescripcion("B").withFechaCreacion(now)
                .build();

        EntidadDTO target = new EntidadDTO();
        EntidadDTO result = mapper.mapReverse(entidad, target);

        assertSame(target, result);
        assertEquals(2L, result.getId());
        assertEquals("A", result.getNombre());
        assertEquals("B", result.getDescripcion());
        assertEquals(now, result.getFechaCreacion());
    }

    @Test
    @DisplayName("mapTo(EntidadDTO) crea Entidad con los campos correctos")
    void mapTo_createsDomain() {
        LocalDateTime now = LocalDateTime.now();
        EntidadDTO dto = new EntidadDTO();
        dto.setId(7L);
        dto.setNombre("Nombre");
        dto.setDescripcion("Descripcion");
        dto.setFechaCreacion(now);

        Entidad entidad = mapper.mapTo(dto);

        assertNotNull(entidad);
        assertEquals(7L, entidad.getId());
        assertEquals("Nombre", entidad.getNombre());
        assertEquals("Descripcion", entidad.getDescripcion());
        assertEquals(now, entidad.getFechaCreacion());
    }

    @Test
    @DisplayName("mapTo(EntidadDTO, Entidad) ignora la instancia destino y devuelve una nueva")
    void mapTo_overload_returnsNewInstance() {
        LocalDateTime now = LocalDateTime.now();
        EntidadDTO dto = new EntidadDTO();
        dto.setId(99L);
        dto.setNombre("X");
        dto.setDescripcion("Y");
        dto.setFechaCreacion(now);

        Entidad existing = new Entidad.Builder()
                .withId(1L).withNombre("Old").withDescripcion("Old").withFechaCreacion(now.minusDays(1))
                .build();

        Entidad mapped = mapper.mapTo(dto, existing);

        assertNotSame(existing, mapped);
        assertEquals(99L, mapped.getId());
        assertEquals("X", mapped.getNombre());
        assertEquals("Y", mapped.getDescripcion());
        assertEquals(now, mapped.getFechaCreacion());
    }
}
