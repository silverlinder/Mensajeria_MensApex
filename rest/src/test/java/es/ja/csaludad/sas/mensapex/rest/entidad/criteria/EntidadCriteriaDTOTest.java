package es.ja.csaludad.sas.mensapex.rest.entidad.criteria;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntidadCriteriaDTOTest {

    @Test
    @DisplayName("Estado por defecto")
    void defaultState() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        assertNull(dto.getNombre());
        assertNull(dto.getDescripcion());
        assertNotNull(dto.getSort());
        assertTrue(dto.getSort().isEmpty());
    }

    @Test
    @DisplayName("Setters y getters")
    void settersGetters() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setNombre("Juan");
        dto.setDescripcion("Cardio");
        dto.setSort(new ArrayList<>(List.of("nombre,asc", "descripcion,desc")));

        assertEquals("Juan", dto.getNombre());
        assertEquals("Cardio", dto.getDescripcion());
        assertEquals(List.of("nombre,asc", "descripcion,desc"), dto.getSort());
    }

    @Test
    @DisplayName("Mutabilidad de sort y reasignación")
    void sortMutabilityAndReassign() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.getSort().add("nombre,asc");
        assertEquals(1, dto.getSort().size());
        List<String> nueva = List.of("descripcion,desc");
        dto.setSort(new ArrayList<>(nueva));
        assertEquals(nueva, dto.getSort());
    }
}
