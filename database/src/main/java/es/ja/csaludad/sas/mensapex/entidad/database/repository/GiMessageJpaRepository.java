package es.ja.csaludad.sas.mensapex.entidad.database.repository;

import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
import es.ja.csaludad.sas.mensapex.domain.entity.repository.GiMessageRepository;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.GiMessageEntity;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.JpaContext;
import es.ja.csalud.sas.framework.infraestructura.database.jpa.repository.JpaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class GiMessageJpaRepository extends JpaRepository<GiMessageEntity, GiMessage, Long> implements GiMessageRepository {

    @Inject
    public GiMessageJpaRepository(EntityManager em, Mapper<GiMessage, GiMessageEntity> mapper) {
        super(new JpaContext<>(em, mapper, GiMessageEntity.class, GiMessage.class));
    }
}
