package es.ja.csaludad.sas.mensapex.rest.entidad.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EntidadDTOTest {

    @Test
    @DisplayName("Estado por defecto")
    void defaultState() {
        EntidadDTO dto = new EntidadDTO();
        assertNull(dto.getId());
        assertNull(dto.getNombre());
        assertNull(dto.getDescripcion());
        assertNull(dto.getFechaCreacion());
    }

    @Test
    @DisplayName("Setters y getters")
    void settersGetters() {
        EntidadDTO dto = new EntidadDTO();
        LocalDateTime now = LocalDateTime.now();

        dto.setId(42L);
        dto.setNombre("Hospital Central");
        dto.setDescripcion("Urgencias 24h");
        dto.setFechaCreacion(now);

        assertEquals(42L, dto.getId());
        assertEquals("Hospital Central", dto.getNombre());
        assertEquals("Urgencias 24h", dto.getDescripcion());
        assertEquals(now, dto.getFechaCreacion());
    }

    @Test
    @DisplayName("Reasignaciones sucesivas")
    void multipleAssignments() {
        EntidadDTO dto = new EntidadDTO();

        dto.setId(1L);
        dto.setId(2L);
        assertEquals(2L, dto.getId());

        dto.setNombre("A");
        dto.setNombre("B");
        assertEquals("B", dto.getNombre());

        dto.setDescripcion("X");
        dto.setDescripcion("Y");
        assertEquals("Y", dto.getDescripcion());

        LocalDateTime t1 = LocalDateTime.now().minusDays(1);
        LocalDateTime t2 = LocalDateTime.now();
        dto.setFechaCreacion(t1);
        dto.setFechaCreacion(t2);
        assertEquals(t2, dto.getFechaCreacion());
    }
}
