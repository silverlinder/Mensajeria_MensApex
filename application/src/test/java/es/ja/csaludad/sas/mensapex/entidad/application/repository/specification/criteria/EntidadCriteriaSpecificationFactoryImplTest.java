package es.ja.csaludad.sas.mensapex.entidad.application.repository.specification.criteria;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.EntidadSpecificationBundle;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EntidadCriteriaSpecificationFactoryImplTest {

    private EntidadSpecificationBundle specificationBundle;
    private EntidadCriteriaSpecificationFactoryImpl factory;

    @BeforeEach
    void setUp() {
        specificationBundle = mock(EntidadSpecificationBundle.class);
        factory = new EntidadCriteriaSpecificationFactoryImpl(specificationBundle);
    }

    @Test
    @DisplayName("Debe devolver la especificación vacía si no hay filtros")
    void testSinFiltros() {
        EntidadCriteria criteria = new EntidadCriteria();

        Specification<Entidad, Long> emptySpec = mock(Specification.class);
        when(specificationBundle.emptySpecification()).thenReturn(emptySpec);

        Specification<Entidad, Long> result = factory.createCriteriaSpecification(criteria);

        assertNotNull(result);
        assertSame(emptySpec, result);
        verify(specificationBundle).emptySpecification();
        verify(specificationBundle, never()).filterByName(anyString());
        verify(specificationBundle, never()).filterByDescription(anyString());
    }

    @Test
    @DisplayName("Debe añadir filtro por nombre si está presente")
    void testFiltroPorNombre() {
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.setName("Hospital");

        Specification<Entidad, Long> emptySpec = mock(Specification.class);
        Specification<Entidad, Long> nameSpec = mock(Specification.class);

        when(specificationBundle.emptySpecification()).thenReturn(emptySpec);
        when(specificationBundle.filterByName("Hospital")).thenReturn(nameSpec);
        when(emptySpec.and(nameSpec)).thenReturn(nameSpec); // Simula combinación

        Specification<Entidad, Long> result = factory.createCriteriaSpecification(criteria);

        assertSame(nameSpec, result);
        verify(specificationBundle).filterByName("Hospital");
        verify(specificationBundle, never()).filterByDescription(anyString());
    }

    @Test
    @DisplayName("Debe añadir filtro por descripción si está presente")
    void testFiltroPorDescripcion() {
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.setDescription("Urgencias");

        Specification<Entidad, Long> emptySpec = mock(Specification.class);
        Specification<Entidad, Long> descSpec = mock(Specification.class);

        when(specificationBundle.emptySpecification()).thenReturn(emptySpec);
        when(specificationBundle.filterByDescription("Urgencias")).thenReturn(descSpec);
        when(emptySpec.and(descSpec)).thenReturn(descSpec);

        Specification<Entidad, Long> result = factory.createCriteriaSpecification(criteria);

        assertSame(descSpec, result);
        verify(specificationBundle).filterByDescription("Urgencias");
        verify(specificationBundle, never()).filterByName(anyString());
    }

    @Test
    @DisplayName("Debe añadir filtros de nombre y descripción si ambos están presentes")
    void testFiltrosCombinados() {
        EntidadCriteria criteria = new EntidadCriteria();
        criteria.setName("Hospital");
        criteria.setDescription("Urgencias");

        Specification<Entidad, Long> emptySpec = mock(Specification.class);
        Specification<Entidad, Long> nameSpec = mock(Specification.class);
        Specification<Entidad, Long> descSpec = mock(Specification.class);
        Specification<Entidad, Long> combinedSpec = mock(Specification.class);

        when(specificationBundle.emptySpecification()).thenReturn(emptySpec);
        when(specificationBundle.filterByName("Hospital")).thenReturn(nameSpec);
        when(specificationBundle.filterByDescription("Urgencias")).thenReturn(descSpec);

        // Encadenado: empty -> name -> description
        when(emptySpec.and(nameSpec)).thenReturn(nameSpec);
        when(nameSpec.and(descSpec)).thenReturn(combinedSpec);

        Specification<Entidad, Long> result = factory.createCriteriaSpecification(criteria);

        assertSame(combinedSpec, result);
        verify(specificationBundle).filterByName("Hospital");
        verify(specificationBundle).filterByDescription("Urgencias");
    }
}
