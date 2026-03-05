//package es.ja.csaludad.sas.mensapex.domain.entidad;
//
//import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@DisplayName("Tests de la clase Entidad")
//class GiMessageTest {
//
//    @Test
//    @DisplayName("Debe construir correctamente una Entidad con todos los campos")
//    void testBuilderCreatesEntidadCorrectly() {
//        Long id = 100L;
//        String nombre = "Centro de Salud Norte";
//        String descripcion = "Entidad asistencial";
//        LocalDateTime fecha = LocalDateTime.of(2023, 5, 10, 14, 30);
//
//        GiMessage giMessage = new GiMessage.Builder()
//                .withId(id)
//                .withNombre(nombre)
//                .withDescripcion(descripcion)
//                .withFechaCreacion(fecha)
//                .build();
//
//        assertEquals(id, giMessage.getId());
//        assertEquals(nombre, giMessage.getNombre());
//        assertEquals(descripcion, giMessage.getDescripcion());
//        assertEquals(fecha, giMessage.getFechaCreacion());
//        assertEquals(id, giMessage.getDomainId());
//    }
//
//    @Test
//    @DisplayName("Debe lanzar excepción al intentar modificar el ID mediante setDomainId()")
//    void testSetDomainIdThrowsException() {
//        GiMessage giMessage = new GiMessage.Builder()
//                .withId(1L)
//                .withNombre("Entidad")
//                .withDescripcion("Descripción")
//                .withFechaCreacion(LocalDateTime.now())
//                .build();
//
//        assertThrows(UnsupportedOperationException.class, () -> giMessage.setDomainId(2L));
//    }
//}
