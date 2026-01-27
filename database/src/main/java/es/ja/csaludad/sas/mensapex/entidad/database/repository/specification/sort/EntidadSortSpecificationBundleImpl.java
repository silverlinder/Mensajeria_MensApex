package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.sort;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.sort.EntidadSortSpecificationBundle;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntidadSortSpecificationBundleImpl implements EntidadSortSpecificationBundle {
    @Override
    public SortSpecification sortByName(SortDirection direction) {
        return new EntidadByNameSortSpecification(direction);
    }

    @Override
    public SortSpecification sortByDescription(SortDirection direction) {
        return new EntidadByDescriptionSortSpecification(direction);
    }
}
