package es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csalud.sas.framework.domain.repository.specification.Specification;
import es.ja.csalud.sas.framework.domain.repository.specification.SpecificationBundle;

public interface EntidadSpecificationBundle extends SpecificationBundle<Entidad, Long> {

    Specification<Entidad, Long> filterByName(String name);

    Specification<Entidad, Long> filterByDescription(String description);


}
