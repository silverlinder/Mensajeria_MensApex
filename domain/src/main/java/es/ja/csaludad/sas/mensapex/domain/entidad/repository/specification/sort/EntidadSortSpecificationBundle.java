package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.sort;

import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecificationBundle;

public interface EntidadSortSpecificationBundle extends SortSpecificationBundle {
    SortSpecification sortByName(SortDirection direction);

    SortSpecification sortByDescription(SortDirection direction);
}
