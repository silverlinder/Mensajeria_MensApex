package es.ja.csaludad.sas.mensapex.entidad.application;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.EntidadRepository;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSortSpecificationFactory;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSpecificationFactory;
import es.ja.csalud.sas.framework.domain.exception.BusinessException;
import es.ja.csalud.sas.framework.domain.repository.operation.DistinctOperation;
import es.ja.csalud.sas.framework.domain.repository.result.SingleResult;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetEntidadUseCaseImplTest {

    private EntidadRepository entidadRepository;
    private EntidadCriteriaSpecificationFactory criteriaFactory;
    private EntidadCriteriaSortSpecificationFactory sortFactory;
    private GetEntidadUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        entidadRepository = mock(EntidadRepository.class);
        criteriaFactory = mock(EntidadCriteriaSpecificationFactory.class);
        sortFactory = mock(EntidadCriteriaSortSpecificationFactory.class);
        useCase = new GetEntidadUseCaseImpl(entidadRepository, criteriaFactory, sortFactory);
    }

    @Test
    @DisplayName("Debe devolver la entidad cuando existe")
    void testGetEntidadExistente() {
        Long id = 1L;
        Entidad entidadMock = new Entidad.Builder()
                .withId(id)
                .withNombre("Entidad A")
                .withDescripcion("Descripción")
                .withFechaCreacion(LocalDateTime.now())
                .build();

        SingleResult<Entidad> singleResult = mock(SingleResult.class);
        when(singleResult.singleOrDefault()).thenReturn(Optional.of(entidadMock));
        when(entidadRepository.findById(id)).thenReturn(singleResult);

        Entidad result = useCase.get(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(entidadRepository).findById(id);
        verify(singleResult).singleOrDefault();
    }

    @Test
    @DisplayName("Debe lanzar BusinessException si la entidad no existe")
    void testGetEntidadInexistente() {
        Long id = 999L;

        SingleResult<Entidad> singleResult = mock(SingleResult.class);
        when(singleResult.singleOrDefault()).thenReturn(Optional.empty());
        when(entidadRepository.findById(id)).thenReturn(singleResult);

        BusinessException exception = assertThrows(BusinessException.class, () -> useCase.get(id));
        assertTrue(exception.getMessage().contains("Entidad no encontrada"));
        verify(entidadRepository).findById(id);
        verify(singleResult).singleOrDefault();
    }

    @Test
    @DisplayName("Debe devolver todas las entidades sin orden")
    void testGetAllSinOrden() {
        EntidadCriteria criteria = new EntidadCriteria();

        Specification<Entidad, Long> spec = mock(Specification.class);
        when(criteriaFactory.createCriteriaSpecification(criteria)).thenReturn(spec);
        when(sortFactory.createSortListFrom(criteria)).thenReturn(List.of());

        DistinctOperation<Entidad> findClause = mock(DistinctOperation.class);
        Entidad entidad1 = new Entidad.Builder().withId(1L).withNombre("A").build();
        Entidad entidad2 = new Entidad.Builder().withId(2L).withNombre("B").build();
        when(findClause.asCollection()).thenReturn(List.of(entidad1, entidad2));

        when(entidadRepository.findBy(spec)).thenReturn(findClause);

        Collection<Entidad> result = useCase.getAll(criteria);

        assertEquals(2, result.size());
        assertTrue(result.contains(entidad1));
        assertTrue(result.contains(entidad2));

        verify(findClause, never()).orderBy();
        verify(findClause).asCollection();
    }

    @Test
    @DisplayName("Debe devolver todas las entidades con orden aplicado")
    void testGetAllConOrden() {
        EntidadCriteria criteria = new EntidadCriteria();

        Specification<Entidad, Long> spec = mock(Specification.class);
        when(criteriaFactory.createCriteriaSpecification(criteria)).thenReturn(spec);

        SortSpecification sortSpec = mock(SortSpecification.class);
        when(sortFactory.createSortListFrom(criteria)).thenReturn(List.of(sortSpec));

        DistinctOperation<Entidad> findClause = mock(DistinctOperation.class);
        when(findClause.asCollection()).thenReturn(List.of());

        when(entidadRepository.findBy(spec)).thenReturn(findClause);

        useCase.getAll(criteria);

        // Forzar a Mockito a usar la sobrecarga varargs haciendo cast
        verify(findClause).orderBy((SortSpecification[]) any());
        verify(findClause).asCollection();
    }

}
