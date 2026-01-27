package es.ja.csaludad.sas.mensapex.entidad.database.repository;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.EntidadRepository;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.JpaContext;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.JpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class EntidadJpaRepository extends JpaRepository<EntidadEntity, Entidad, Long> implements EntidadRepository {

    @Inject
    public EntidadJpaRepository(EntityManager em, Mapper<Entidad, EntidadEntity> mapper) {
        super(new JpaContext<>(em, mapper, EntidadEntity.class, Entidad.class));
    }
}
