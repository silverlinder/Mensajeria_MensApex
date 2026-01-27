package es.ja.csaludad.sas.mensapex.entidad.application.repository.specification.criteria;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.EntidadSpecificationBundle;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSpecificationFactory;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EntidadCriteriaSpecificationFactoryImpl implements EntidadCriteriaSpecificationFactory {

    private final EntidadSpecificationBundle entidadSpecificationBundle;

    @Inject
    public EntidadCriteriaSpecificationFactoryImpl(EntidadSpecificationBundle entidadSpecificationBundle) {
        this.entidadSpecificationBundle = entidadSpecificationBundle;
    }

    @Override
    public Specification<Entidad, Long> createCriteriaSpecification(EntidadCriteria hospitalCriteria) {
        Specification<Entidad, Long> entidadSpecification = entidadSpecificationBundle.emptySpecification();

        if (hospitalCriteria.getName() != null) {
            entidadSpecification = entidadSpecification.and(
                    entidadSpecificationBundle.filterByName(
                            hospitalCriteria.getName()
                    )
            );
        }


        if (hospitalCriteria.getDescription() != null) {
            entidadSpecification = entidadSpecification.and(
                    entidadSpecificationBundle.filterByDescription(
                            hospitalCriteria.getDescription()
                    )
            );
        }


        return entidadSpecification;
    }
}
