package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;

import java.util.Collection;

public interface EntidadCriteriaSortSpecificationFactory {
    Collection<SortSpecification> createSortListFrom(EntidadCriteria criteria);
}
