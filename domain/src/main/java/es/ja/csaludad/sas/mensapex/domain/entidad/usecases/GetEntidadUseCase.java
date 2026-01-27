package es.ja.csaludad.sas.mensapex.domain.entidad.usecases;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;

import java.util.Collection;

public interface GetEntidadUseCase {
    Entidad get(Long idEntidad);

    Collection<Entidad> getAll(EntidadCriteria entidadCriteria);
}
