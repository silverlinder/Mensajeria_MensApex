package es.ja.csaludad.sas.mensapex.entidad.database.repository.specification;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.EntidadSpecificationBundle;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.filter.EntidadFilterByDescriptionJpaSpecification;
import es.ja.csaludad.sas.mensapex.entidad.database.repository.specification.filter.EntidadFilterByNameJpaSpecification;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.specification.JpaSpecificationBundle;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntidadSpecificationBundleImpl implements EntidadSpecificationBundle,
        JpaSpecificationBundle<EntidadEntity, Entidad, Long> {

    @Override
    public Specification<Entidad, Long> filterByName(String name) {
        return new EntidadFilterByNameJpaSpecification(name);
    }

    @Override
    public Specification<Entidad, Long> filterByDescription(String description) {
        return new EntidadFilterByDescriptionJpaSpecification(description);
    }
}
