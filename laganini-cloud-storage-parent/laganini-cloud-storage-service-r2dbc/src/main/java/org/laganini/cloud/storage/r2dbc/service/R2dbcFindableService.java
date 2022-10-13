package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional(readOnly = true)
public interface R2dbcFindableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, FindableService<ID, ENTITY>
{

    @Override
    default Flux<ENTITY> findAll(Collection<ID> ids) {
        return getRepository().findAllById(ids);
    }

    @Override
    default Mono<ENTITY> find(ID id) {
        return getRepository().findById(id);
    }

}
