package es.ja.csaludad.sas.mensapex.entidad.application.repository.specification.criteria.sort;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.factory.EntidadCriteriaSortSpecificationFactory;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.sort.EntidadSortSpecificationBundle;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortSpecification;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class EntidadCriteriaSortSpecificationFactoryImpl implements EntidadCriteriaSortSpecificationFactory {
    private final EntidadSortSpecificationBundle entidadSortSpecificationBundle;

    @Inject
    public EntidadCriteriaSortSpecificationFactoryImpl(EntidadSortSpecificationBundle entidadSortSpecificationBundle) {
        this.entidadSortSpecificationBundle = entidadSortSpecificationBundle;
    }

    @Override
    public Collection<SortSpecification> createSortListFrom(EntidadCriteria entidadCriteria) {
        List<SortSpecification> specs = new ArrayList<>();

        entidadCriteria.getSort().forEach(sortOrder -> {
            SortSpecification sortSpec = switch (sortOrder.getField()) {
                case "name" -> entidadSortSpecificationBundle.sortByName(sortOrder.getDir());
                case "description" -> entidadSortSpecificationBundle.sortByDescription(sortOrder.getDir());
                default -> throw new IllegalArgumentException("Unknown sort field: " + sortOrder.getField());
            };
            specs.add(sortSpec);

        });

        return specs;
    }


}
