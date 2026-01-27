package es.ja.csaludad.sas.mensapex.entidad.application.repository.specification.criteria.sort;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.sort.EntidadSortSpecificationBundle;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class EntidadCriteriaSortSpecificationFactoryImplTest {

    private EntidadSortSpecificationBundle sortBundle;
    private EntidadCriteriaSortSpecificationFactoryImpl factory;

    @BeforeEach
    void setUp() {
        sortBundle = mock(EntidadSortSpecificationBundle.class);
        factory = new EntidadCriteriaSortSpecificationFactoryImpl(sortBundle);
    }

    @Test
    @DisplayName("Debe devolver spec de orden por nombre")
    void testSortByName() {
        // Arrange
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.addSort("name", SortDirection.ASC);

        SortSpecification mockSpec = mock(SortSpecification.class);
        when(sortBundle.sortByName(SortDirection.ASC)).thenReturn(mockSpec);

        // Act
        Collection<SortSpecification> result = factory.createSortListFrom(criteria);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(mockSpec));
        verify(sortBundle).sortByName(SortDirection.ASC);
        verify(sortBundle, never()).sortByDescription(any());
    }

    @Test
    @DisplayName("Debe devolver spec de orden por descripción")
    void testSortByDescription() {
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.addSort("description", SortDirection.DESC);

        SortSpecification mockSpec = mock(SortSpecification.class);
        when(sortBundle.sortByDescription(SortDirection.DESC)).thenReturn(mockSpec);

        Collection<SortSpecification> result = factory.createSortListFrom(criteria);

        assertEquals(1, result.size());
        assertTrue(result.contains(mockSpec));
        verify(sortBundle).sortByDescription(SortDirection.DESC);
        verify(sortBundle, never()).sortByName(any());
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException si el campo es desconocido")
    void testCampoDesconocido() {
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.addSort("otroCampo", SortDirection.ASC);

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> factory.createSortListFrom(criteria)
        );
        assertTrue(ex.getMessage().contains("Unknown sort field"));
        verifyNoInteractions(sortBundle);
    }
}
