package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.sort;

import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.enitity.JpaEntity;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.specification.sort.JpaSortSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

public abstract class AbstractMetamodelSortSpecification<T extends JpaEntity<?>, X>
        implements JpaSortSpecification<T> {

    private final SortDirection direction;
    private final SingularAttribute<? super T, X> attribute;

    protected AbstractMetamodelSortSpecification(SortDirection direction,
                                                 SingularAttribute<? super T, X> attribute) {
        this.direction = direction;
        this.attribute = attribute;
    }

    @Override
    public Order asOrder(CriteriaBuilder cb, Root<T> root) {
        Expression<?> expr = root.get(attribute);

        return (direction == SortDirection.DESC) ? cb.desc(expr) : cb.asc(expr);
    }
}
