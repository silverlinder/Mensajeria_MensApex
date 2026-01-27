package es.ja.csaludad.sas.mensapex.rest.entidad.mapper;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.rest.entidad.criteria.EntidadCriteriaDTO;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntidadCriteriaMapperTest {

    @Test
    @DisplayName("Mapea nombre y descripción; sort null -> sin órdenes")
    void mapsFields_noSort() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setNombre("Juan");
        dto.setDescripcion("Cardio");
        dto.setSort(null);

        EntidadCriteria c = EntidadCriteriaMapper.toDomain(dto);

        assertEquals("Juan", c.getName());
        assertEquals("Cardio", c.getDescription());
        assertTrue(c.getSort().isEmpty());
    }

    @Test
    @DisplayName("Sort válido: nombre asc y descripción desc")
    void mapsValidSorts() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of("nombre,asc", "descripcion,desc")));

        EntidadCriteria c = EntidadCriteriaMapper.toDomain(dto);

        assertEquals(2, c.getSort().size());
        assertEquals("name", c.getSort().get(0).getField());
        assertEquals(SortDirection.ASC, c.getSort().get(0).getDir());
        assertEquals("description", c.getSort().get(1).getField());
        assertEquals(SortDirection.DESC, c.getSort().get(1).getDir());
    }

    @Test
    @DisplayName("Ignora entradas vacías o en blanco")
    void ignoresBlankEntries() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of("", "   ")));

        EntidadCriteria c = EntidadCriteriaMapper.toDomain(dto);

        assertTrue(c.getSort().isEmpty());
    }

    @Test
    @DisplayName("Campo de orden no soportado -> IllegalArgumentException")
    void unknownFieldThrows() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of("otro,asc")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> EntidadCriteriaMapper.toDomain(dto));
        assertTrue(ex.getMessage().contains("Campo de ordenación no soportado"));
    }

    @Test
    @DisplayName("Dirección inválida -> IllegalArgumentException")
    void invalidDirectionThrows() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of("nombre,up")));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> EntidadCriteriaMapper.toDomain(dto));
        assertTrue(ex.getMessage().contains("Dirección inválida"));
    }

    @Test
    @DisplayName("Entrada solo con campo (sin dirección) -> no añade orden")
    void fieldOnlyAddsNothing() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of("nombre")));

        EntidadCriteria c = EntidadCriteriaMapper.toDomain(dto);

        assertTrue(c.getSort().isEmpty());
    }

    @Test
    @DisplayName("Mayúsculas/minúsculas y espacios son tolerados")
    void trimsAndUppercasesDirection() {
        EntidadCriteriaDTO dto = new EntidadCriteriaDTO();
        dto.setSort(new ArrayList<>(List.of(" nombre , Asc ", " descripcion ,  deSc ")));

        EntidadCriteria c = EntidadCriteriaMapper.toDomain(dto);

        assertEquals(2, c.getSort().size());
        assertEquals("name", c.getSort().get(0).getField());
        assertEquals(SortDirection.ASC, c.getSort().get(0).getDir());
        assertEquals("description", c.getSort().get(1).getField());
        assertEquals(SortDirection.DESC, c.getSort().get(1).getDir());
    }
}
