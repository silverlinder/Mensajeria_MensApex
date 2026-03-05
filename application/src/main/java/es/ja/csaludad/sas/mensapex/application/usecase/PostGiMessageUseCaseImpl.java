package es.ja.csaludad.sas.mensapex.application.usecase;

import es.ja.csalud.sas.framework.domain.repository.result.SingleResult;
import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;
import es.ja.csaludad.sas.mensapex.domain.entity.repository.GiMessageRepository;
import es.ja.csaludad.sas.mensapex.domain.entity.usecases.PostGiMessageUseCase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PostGiMessageUseCaseImpl implements PostGiMessageUseCase {

    private final GiMessageRepository giMessageRepository;

    @Inject
    public PostGiMessageUseCaseImpl(GiMessageRepository giMessageRepository) {
        this.giMessageRepository = giMessageRepository;
    }

    @Override
    public GiMessage insert(GiMessage mappeEntity) {
        Long id = giMessageRepository.create(mappeEntity);
        SingleResult<GiMessage> result = giMessageRepository.findById(id);
        return result.singleOrDefault().orElse(null);
    }
}
