package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.filter;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity_;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.specification.CompositeJpaSpecification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EntidadFilterByNameJpaSpecification implements CompositeJpaSpecification<EntidadEntity, Entidad, Long> {
    private final String nombre;

    public EntidadFilterByNameJpaSpecification(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public Predicate asPredicate(CriteriaBuilder criteriaBuilder, Root<EntidadEntity> root, CriteriaQuery<?> criteriaQuery) {
        return criteriaBuilder.equal(
                criteriaBuilder.lower(
                        root.get(EntidadEntity_.nombre)
                ), this.nombre.toLowerCase()
        );

    }
}
