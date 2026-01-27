package es.ja.csaludad.sas.mensapex.entidad.database;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.EntidadEntity;
import es.ja.csalud.sas.framework.domain.mapper.BaseMapper;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntidadMapper extends BaseMapper<Entidad, EntidadEntity> implements Mapper<Entidad, EntidadEntity> {

    @Override
    public Entidad mapReverse(EntidadEntity entidadEntity) {
        return new Entidad.Builder()
                .withId(entidadEntity.getId())
                .withNombre(entidadEntity.getNombre())
                .withDescripcion(entidadEntity.getDescripcion())
                .withFechaCreacion(entidadEntity.getFechaCreacion())
                .build();
    }

    @Override
    public Entidad mapReverse(EntidadEntity entidadEntity, Entidad entidad) {
        return mapReverse(entidadEntity);
    }

    @Override
    public EntidadEntity mapTo(Entidad entidad) {
        EntidadEntity entidadEntity = new EntidadEntity();
        entidadEntity.setId(entidad.getId());
        entidadEntity.setNombre(entidad.getNombre());
        entidadEntity.setDescripcion(entidad.getDescripcion());
        entidadEntity.setFechaCreacion(entidad.getFechaCreacion());
        return entidadEntity;
    }

    @Override
    public EntidadEntity mapTo(Entidad entidad, EntidadEntity entidadEntity) {
        entidadEntity.setId(entidad.getId());
        entidadEntity.setNombre(entidad.getNombre());
        entidadEntity.setDescripcion(entidad.getDescripcion());
        entidadEntity.setFechaCreacion(entidad.getFechaCreacion());
        return entidadEntity;
    }
}
