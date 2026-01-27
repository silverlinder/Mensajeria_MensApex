package es.ja.csaludad.sas.mensapex.entidad.database;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EntityManagerProducer {

    @PersistenceContext(unitName = "jpa-unit")
    private EntityManager entityManager;


    @Produces
    @ApplicationScoped
    public EntityManager createEntityManager() {
        return entityManager;
    }

}