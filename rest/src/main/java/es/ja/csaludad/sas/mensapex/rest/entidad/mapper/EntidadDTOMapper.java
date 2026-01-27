package es.ja.csaludad.sas.mensapex.rest.entidad.mapper;

import es.ja.csaludad.sas.mensapex.domain.entidad.Entidad;
import es.ja.csaludad.sas.mensapex.rest.entidad.dto.EntidadDTO;
import es.ja.csalud.sas.framework.domain.mapper.BaseMapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntidadDTOMapper extends BaseMapper<EntidadDTO, Entidad> {

    @Override
    public EntidadDTO mapReverse(Entidad entidad) {
        EntidadDTO entidadDTO = new EntidadDTO();
        return mapReverse(entidad, entidadDTO);
    }

    @Override
    public EntidadDTO mapReverse(Entidad entidad, EntidadDTO entidadDTO) {
        entidadDTO.setId(entidad.getId());
        entidadDTO.setNombre(entidad.getNombre());
        entidadDTO.setDescripcion(entidad.getDescripcion());
        entidadDTO.setFechaCreacion(entidad.getFechaCreacion());
        return entidadDTO;
    }

    @Override
    public Entidad mapTo(EntidadDTO entidadDTO) {
        return new Entidad.Builder()
                .withId(entidadDTO.getId())
                .withNombre(entidadDTO.getNombre())
                .withDescripcion(entidadDTO.getDescripcion())
                .withFechaCreacion(entidadDTO.getFechaCreacion())
                .build();
    }

    @Override
    public Entidad mapTo(EntidadDTO entidadDTO, Entidad entidad) {
        return mapTo(entidadDTO);
    }
}
