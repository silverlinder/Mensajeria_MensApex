package es.ja.csaludad.sas.mensapex.rest.entidad.mapper;

import es.ja.csaludad.sas.mensapex.domain.entidad.repository.specification.criteria.entities.EntidadCriteria;
import es.ja.csaludad.sas.mensapex.rest.entidad.criteria.EntidadCriteriaDTO;
import es.ja.csalud.sas.framework.domain.repository.specification.sorting.SortDirection;

import java.util.List;
import java.util.Map;

public final class EntidadCriteriaMapper {

    private static final Map<String, String> API_TO_DOMAIN = Map.of(
            "nombre", "name",
            "descripcion", "description"
    );

    private EntidadCriteriaMapper() { /* utility class */ }

    public static EntidadCriteria toDomain(EntidadCriteriaDTO dto) {
        EntidadCriteria criteria = new EntidadCriteria();

        criteria.setName(dto.getNombre());
        criteria.setDescription(dto.getDescripcion());

        applySort(criteria, dto.getSort());

        return criteria;
    }


    private static void applySort(EntidadCriteria criteria, List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) return;

        for (String param : sortParams) {
            if (param == null || param.isBlank()) continue;
            
            String[] parts = param.split(",", 2);
            String apiField = parts[0].trim();

            String domainField = API_TO_DOMAIN.get(apiField);
            if (domainField == null) {
                throw new IllegalArgumentException("Campo de ordenación no soportado: " + apiField);
            }

            if (parts.length > 1 && parts[1] != null) {
                String d = parts[1].trim().toUpperCase();
                switch (d) {
                    case "ASC" -> criteria.addSort(domainField, SortDirection.ASC);
                    case "DESC" -> criteria.addSort(domainField, SortDirection.DESC);
                    default -> throw new IllegalArgumentException("Dirección inválida: " + d);
                }
            }


        }
    }
}
