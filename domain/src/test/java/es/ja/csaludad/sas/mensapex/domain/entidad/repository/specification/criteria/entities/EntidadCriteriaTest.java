package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities;

import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntidadCriteriaTest {

    @Test
    @DisplayName("Setters y getters de name/description")
    void gettersSetters() {
        EntidadCriteria c = new EntidadCriteria();
        c.setName("n");
        c.setDescription("d");
        assertEquals("n", c.getName());
        assertEquals("d", c.getDescription());
    }

    @Test
    @DisplayName("addSort añade órdenes y getSort es inmutable")
    void addSortAndImmutability() {
        EntidadCriteria c = new EntidadCriteria();
        c.addSort("name", SortDirection.ASC);
        c.addSort("description", SortDirection.DESC);

        List<EntidadCriteria.SortOrder> sort = c.getSort();
        assertEquals(2, sort.size());
        assertEquals("name", sort.get(0).getField());
        assertEquals(SortDirection.ASC, sort.get(0).getDir());
        assertEquals("description", sort.get(1).getField());
        assertEquals(SortDirection.DESC, sort.get(1).getDir());

        assertThrows(UnsupportedOperationException.class, () -> sort.add(
                new EntidadCriteria.SortOrder("x", SortDirection.ASC)));
    }

    @Test
    @DisplayName("SortOrder toString devuelve 'field DIR'")
    void sortOrderToString() {
        EntidadCriteria.SortOrder so =
                new EntidadCriteria.SortOrder("name", SortDirection.ASC);
        assertEquals("name ASC", so.toString());
    }
}
