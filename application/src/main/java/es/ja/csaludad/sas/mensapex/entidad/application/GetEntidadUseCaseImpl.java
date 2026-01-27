package es.ja.csaludad.sas.mensapex.entidad.application;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.EntidadRepository;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSortSpecificationFactory;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSpecificationFactory;
import es.ja.csaludad.sas.mensapex.domain.entidad.usecases.GetEntidadUseCase;
import es.ja.csalud.sas.framework.domain.exception.BusinessException;
import es.ja.csalud.sas.framework.domain.repository.operation.DistinctOperation;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Collection;

@ApplicationScoped
public class GetEntidadUseCaseImpl implements GetEntidadUseCase {

    private final EntidadRepository entidadRepository;
    private final EntidadCriteriaSpecificationFactory entidadCriteriaSpecificationFactory;
    private final EntidadCriteriaSortSpecificationFactory entidadCriteriaSortSpecificationFactory;

    @Inject
    public GetEntidadUseCaseImpl(final EntidadRepository entidadRepository,
                                 final EntidadCriteriaSpecificationFactory entidadCriteriaSpecificationFactory,
                                 final EntidadCriteriaSortSpecificationFactory entidadCriteriaSortSpecificationFactory) {
        this.entidadRepository = entidadRepository;
        this.entidadCriteriaSpecificationFactory = entidadCriteriaSpecificationFactory;
        this.entidadCriteriaSortSpecificationFactory = entidadCriteriaSortSpecificationFactory;
    }

    @Override
    public Entidad get(Long idEntidad) {
        return entidadRepository.findById(idEntidad)
                .singleOrDefault()
                .orElseThrow(() -> new BusinessException("ERRROR-001: Entidad no encontrada: " + idEntidad));
    }

    // Importante tener en cuenta el patrón repositorio y especificación indicado en

    @Override
    public Collection<Entidad> getAll(EntidadCriteria entidadCriteria) {
        Specification<Entidad, Long> criteriaSpecification =
                entidadCriteriaSpecificationFactory.createCriteriaSpecification(entidadCriteria);

        Collection<SortSpecification> orderList =
                entidadCriteriaSortSpecificationFactory.createSortListFrom(entidadCriteria);

        DistinctOperation<Entidad> findClause = entidadRepository.findBy(criteriaSpecification);

        if (orderList.isEmpty()) {
            return findClause.asCollection();
        }

        findClause.orderBy(orderList.toArray(SortSpecification[]::new));
        return findClause.asCollection();
    }

}
