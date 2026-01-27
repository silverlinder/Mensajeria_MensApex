package es.ja.csaludad.sas.mensapex.domain.entidad;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tests de la clase Entidad")
class EntidadTest {

    @Test
    @DisplayName("Debe construir correctamente una Entidad con todos los campos")
    void testBuilderCreatesEntidadCorrectly() {
        Long id = 100L;
        String nombre = "Centro de Salud Norte";
        String descripcion = "Entidad asistencial";
        LocalDateTime fecha = LocalDateTime.of(2023, 5, 10, 14, 30);

        Entidad entidad = new Entidad.Builder()
                .withId(id)
                .withNombre(nombre)
                .withDescripcion(descripcion)
                .withFechaCreacion(fecha)
                .build();

        assertEquals(id, entidad.getId());
        assertEquals(nombre, entidad.getNombre());
        assertEquals(descripcion, entidad.getDescripcion());
        assertEquals(fecha, entidad.getFechaCreacion());
        assertEquals(id, entidad.getDomainId());
    }

    @Test
    @DisplayName("Debe lanzar excepción al intentar modificar el ID mediante setDomainId()")
    void testSetDomainIdThrowsException() {
        Entidad entidad = new Entidad.Builder()
                .withId(1L)
                .withNombre("Entidad")
                .withDescripcion("Descripción")
                .withFechaCreacion(LocalDateTime.now())
                .build();

        assertThrows(UnsupportedOperationException.class, () -> entidad.setDomainId(2L));
    }
}
