package es.ja.csaludad.sas.mensapex.entidad.database;

import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
import es.ja.csaludad.sas.mensapex.entidad.database.entities.GiMessageEntity;
import es.ja.csalud.sas.framework.domain.mapper.BaseMapper;
import es.ja.csalud.sas.framework.domain.mapper.Mapper;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GiMessageMapper extends BaseMapper<GiMessage, GiMessageEntity>
        implements Mapper<GiMessage, GiMessageEntity> {

    @Override
    public GiMessage mapReverse(GiMessageEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GiMessage.Builder()
                .withId(entity.getId())
                .withMgiUid(entity.getMgiUid())
                .withInternalAhRegcess(entity.getInternalAhRegcess())
                .withInternalPacienteSns(entity.getInternalPacienteSns())
                .withMgiPacienteNuhsa(entity.getMgiPacienteNuhsa())
                .withMgiHeadVersion(entity.getMgiHeadVersion())
                .withMgiHeadModule(entity.getMgiHeadModule())
                .withMgiHeadMaco(entity.getMgiHeadMaco())
                .withMgiMesRecGiPet(entity.getMgiMesRecGiPet())
                .withMgiMesEnvGiResp(entity.getMgiMesEnvGiResp())
                .withMgiMesEnvSnsPet(entity.getMgiMesEnvSnsPet())
                .withMgiMesRecSnsResp(entity.getMgiMesRecSnsResp())
                .withMgiObservaciones(entity.getMgiObservaciones())
                .withMgiCodErrorSns(entity.getMgiCodErrorSns())
                .withMgiDescErrorSns(entity.getMgiDescErrorSns())
                .withMgiGiReceived(entity.getMgiGiReceived())
                .withMgiSnsSend(entity.getMgiSnsSend())
                .withMgiSnsReceived(entity.getMgiSnsReceived())
                .withMgiCreated(entity.getMgiCreated())
                .withMgiCreatedBy(entity.getMgiCreatedBy())
                .withMgiUpdated(entity.getMgiUpdated())
                .withMgiUpdatedBy(entity.getMgiUpdatedBy())
                .build();
    }

    @Override
    public GiMessage mapReverse(GiMessageEntity entity, GiMessage giMessage) {
        // Dominio inmutable: ignoramos el segundo parámetro y construimos uno nuevo
        return mapReverse(entity);
    }

    @Override
    public GiMessageEntity mapTo(GiMessage domain) {
        if (domain == null) {
            return null;
        }

        GiMessageEntity entity = new GiMessageEntity();
        return mapTo(domain, entity);
    }

    @Override
    public GiMessageEntity mapTo(GiMessage domain, GiMessageEntity entity) {
        if (domain == null) {
            return null;
        }
        if (entity == null) {
            entity = new GiMessageEntity();
        }

        entity.setId(domain.getId());
        entity.setMgiUid(domain.getMgiUid());
        entity.setInternalAhRegcess(domain.getInternalAhRegcess());
        entity.setInternalPacienteSns(domain.getInternalPacienteSns());
        entity.setMgiPacienteNuhsa(domain.getMgiPacienteNuhsa());
        entity.setMgiHeadVersion(domain.getMgiHeadVersion());
        entity.setMgiHeadModule(domain.getMgiHeadModule());
        entity.setMgiHeadMaco(domain.getMgiHeadMaco());
        entity.setMgiMesRecGiPet(domain.getMgiMesRecGiPet());
        entity.setMgiMesEnvGiResp(domain.getMgiMesEnvGiResp());
        entity.setMgiMesEnvSnsPet(domain.getMgiMesEnvSnsPet());
        entity.setMgiMesRecSnsResp(domain.getMgiMesRecSnsResp());
        entity.setMgiObservaciones(domain.getMgiObservaciones());
        entity.setMgiCodErrorSns(domain.getMgiCodErrorSns());
        entity.setMgiDescErrorSns(domain.getMgiDescErrorSns());
        entity.setMgiGiReceived(domain.getMgiGiReceived());
        entity.setMgiSnsSend(domain.getMgiSnsSend());
        entity.setMgiSnsReceived(domain.getMgiSnsReceived());
        entity.setMgiCreated(domain.getMgiCreated());
        entity.setMgiCreatedBy(domain.getMgiCreatedBy());
        entity.setMgiUpdated(domain.getMgiUpdated());
        entity.setMgiUpdatedBy(domain.getMgiUpdatedBy());

        return entity;
    }
}