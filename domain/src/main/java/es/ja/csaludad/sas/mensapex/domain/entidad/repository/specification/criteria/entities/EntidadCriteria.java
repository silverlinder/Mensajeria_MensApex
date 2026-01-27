package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities;

import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntidadCriteria {

    private String name;
    private String description;
    private final List<SortOrder> sort = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SortOrder> getSort() {
        return Collections.unmodifiableList(sort);
    }

    public void addSort(String field, SortDirection dir) {
        this.sort.add(new SortOrder(field, dir));
    }
    
    public static class SortOrder {
        private final String field;
        private final SortDirection dir;

        public SortOrder(String field, SortDirection dir) {
            this.field = field;
            this.dir = dir;
        }

        public String getField() {
            return field;
        }

        public SortDirection getDir() {
            return dir;
        }

        @Override
        public String toString() {
            return field + " " + dir;
        }
    }
}
