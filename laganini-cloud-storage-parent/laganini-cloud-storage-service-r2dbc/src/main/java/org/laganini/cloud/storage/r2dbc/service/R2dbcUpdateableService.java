package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface R2dbcUpdateableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, UpdateableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> update(ENTITY entity) {
        return beforeUpdate(entity)
                .flatMap(target -> getRepository().save(entity))
                .flatMap(this::afterUpdate);
    }

    default Mono<ENTITY> beforeUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    default Mono<ENTITY> afterUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    default ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setId(before.getId());

        return after;
    }

}
