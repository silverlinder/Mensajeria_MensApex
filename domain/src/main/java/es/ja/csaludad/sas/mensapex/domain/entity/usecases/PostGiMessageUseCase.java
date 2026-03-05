package es.ja.csaludad.sas.mensapex.domain.entity.usecases;

import es.ja.csaludad.sas.mensapex.domain.entity.GiMessage;

public interface PostGiMessageUseCase {
    GiMessage insert(GiMessage mappedEntity);
}
