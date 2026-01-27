package es.ja.csaludad.sas.mensapex.entidad.database.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EntidadEntityTest {

    @Test
    @DisplayName("Constructor vacío debe permitir instanciación y setters/getters deben funcionar")
    void testConstructorVacioYSettersGetters() {
        EntidadEntity entidad = new EntidadEntity();

        Long id = 1L;
        String nombre = "Hospital Central";
        String descripcion = "Entidad de prueba";
        LocalDateTime fecha = LocalDateTime.now();

        entidad.setId(id);
        entidad.setNombre(nombre);
        entidad.setDescripcion(descripcion);
        entidad.setFechaCreacion(fecha);

        assertEquals(id, entidad.getId());
        assertEquals(nombre, entidad.getNombre());
        assertEquals(descripcion, entidad.getDescripcion());
        assertEquals(fecha, entidad.getFechaCreacion());
    }

    @Test
    @DisplayName("Constructor con parámetros debe inicializar los campos correctamente")
    void testConstructorConParametros() {
        String nombre = "Hospital Central";
        String descripcion = "Entidad de prueba";
        LocalDateTime fecha = LocalDateTime.now();

        EntidadEntity entidad = new EntidadEntity(nombre, descripcion, fecha);

        assertNull(entidad.getId()); // No se asigna en el constructor
        assertEquals(nombre, entidad.getNombre());
        assertEquals(descripcion, entidad.getDescripcion());
        assertEquals(fecha, entidad.getFechaCreacion());
    }
}
