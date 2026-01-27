package es.ja.csaludad.sas.mensapex.rest.entidad.criteria;

import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.enums.Explode;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterStyle;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class EntidadCriteriaDTO {

    @QueryParam("nombre")
    @Schema(description = "Filtro por nombre (coincidencia exacta, case insensitive)")
    private String nombre;

    @QueryParam("descripcion")
    @Schema(description = "Filtro por descripcion (coincidencia parcial o completa)")
    private String descripcion;

    @QueryParam("sort")
    @Parameter(
            name = "sort",
            in = ParameterIn.QUERY,
            description = "Orden múltiple. Usa valores repetidos: ?sort=nombre,asc&sort=descripcion,desc",
            style = ParameterStyle.FORM,
            explode = Explode.TRUE,
            schema = @Schema(type = SchemaType.ARRAY, implementation = String.class),
            examples = {
                    @ExampleObject(name = "Nombre asc", value = "[\"nombre,asc\"]"),
                    @ExampleObject(name = "Nombre desc y descripción desc", value = "[\"nombre,desc\",\"descripcion,desc\"]"),
            }
    )
    private List<String> sort = new ArrayList<>();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
