package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.sort;

import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity_;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;

public class EntidadByNameSortSpecification
        extends AbstractMetamodelSortSpecification<EntidadEntity, String> {

    public EntidadByNameSortSpecification(SortDirection direction) {
        super(direction, EntidadEntity_.nombre);
    }
}
