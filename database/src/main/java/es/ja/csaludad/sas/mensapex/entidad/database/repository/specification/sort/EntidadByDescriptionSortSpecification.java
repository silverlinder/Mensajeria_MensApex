package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.sort;

import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity_;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.specification.sort.JpaSortSpecification;

public class EntidadByDescriptionSortSpecification
        extends AbstractMetamodelSortSpecification<EntidadEntity, String>
        implements JpaSortSpecification<EntidadEntity> {

    public EntidadByDescriptionSortSpecification(SortDirection direction) {
        super(direction, EntidadEntity_.descripcion);
    }
}
