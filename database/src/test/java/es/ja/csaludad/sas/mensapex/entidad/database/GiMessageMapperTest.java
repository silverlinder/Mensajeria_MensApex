//package es.ja.csaludad.sas.mensapex.entidad.database;
//
//import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
//import es.ja.csaludad.sas.mensapex.entidad.database.entities.GiMessageEntity;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertSame;
//
//class GiMessageMapperTest {
//
//    private final GiMessageMapper mapper = new GiMessageMapper();
//
//    @Test
//    @DisplayName("mapReverse: EntidadEntity -> Entidad")
//    void mapReverse_createsDomainFromEntity() {
//        LocalDateTime now = LocalDateTime.now();
//        GiMessageEntity entity = new GiMessageEntity();
//        entity.setId(10L);
//        entity.setNombre("Nombre");
//        entity.setDescripcion("Desc");
//        entity.setFechaCreacion(now);
//
//        GiMessage domain = mapper.mapReverse(entity);
//
//        assertNotNull(domain);
//        assertEquals(10L, domain.getId());
//        assertEquals("Nombre", domain.getNombre());
//        assertEquals("Desc", domain.getDescripcion());
//        assertEquals(now, domain.getFechaCreacion());
//    }
//
//    @Test
//    @DisplayName("mapReverse(entity, domain): ignora el segundo parámetro y mapea como mapReverse(entity)")
////    void mapReverse_overload_ignoresTarget() {
//        LocalDateTime now = LocalDateTime.now();
//        GiMessageEntity entity = new GiMessageEntity();
//        entity.setId(5L);
//        entity.setNombre("A");
//        entity.setDescripcion("B");
//        entity.setFechaCreacion(now);
//
//        GiMessage preExisting = new GiMessage.Builder()
//                .withId(999L).withNombre("X").withDescripcion("Y").withFechaCreacion(now.minusDays(1))
//                .build();
//
//        GiMessage result = mapper.mapReverse(entity, preExisting);
//
//        assertEquals(5L, result.getId());
//        assertEquals("A", result.getNombre());
//        assertEquals("B", result.getDescripcion());
//        assertEquals(now, result.getFechaCreacion());
//    }
//
//    @Test
//    @DisplayName("mapTo: Entidad -> EntidadEntity (nuevo)")
//    void mapTo_createsEntityFromDomain() {
//        LocalDateTime now = LocalDateTime.now();
//        GiMessage domain = new GiMessage.Builder()
//                .withId(7L)
//                .withNombre("H1")
//                .withDescripcion("D1")
//                .withFechaCreacion(now)
//                .build();
//
//        GiMessageEntity entity = mapper.mapTo(domain);
//
//        assertNotNull(entity);
//        assertEquals(7L, entity.getId());
//        assertEquals("H1", entity.getNombre());
//        assertEquals("D1", entity.getDescripcion());
//        assertEquals(now, entity.getFechaCreacion());
//    }
//
//    @Test
//    @DisplayName("mapTo(domain, entity): actualiza el entity existente")
//    void mapTo_updatesExistingEntity() {
//        LocalDateTime now = LocalDateTime.now();
//        GiMessage domain = new GiMessage.Builder()
//                .withId(20L)
//                .withNombre("Nuevo")
//                .withDescripcion("Nueva desc")
//                .withFechaCreacion(now)
//                .build();
//
//        GiMessageEntity entity = new GiMessageEntity();
//        entity.setId(1L);
//        entity.setNombre("Viejo");
//        entity.setDescripcion("Vieja desc");
//        entity.setFechaCreacion(now.minusDays(10));
//
//        GiMessageEntity updated = mapper.mapTo(domain, entity);
//
//        assertSame(entity, updated);
//        assertEquals(20L, updated.getId());
//        assertEquals("Nuevo", updated.getNombre());
//        assertEquals("Nueva desc", updated.getDescripcion());
//        assertEquals(now, updated.getFechaCreacion());
//    }
//}
