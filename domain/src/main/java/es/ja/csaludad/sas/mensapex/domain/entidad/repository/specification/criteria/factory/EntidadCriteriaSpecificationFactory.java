package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;

public interface EntidadCriteriaSpecificationFactory {
    Specification<Entidad, Long> createCriteriaSpecification(EntidadCriteria entidadCriteria);
}
