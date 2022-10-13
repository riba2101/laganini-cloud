package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.CreatableService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface R2dbcCreatableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, CreatableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> create(ENTITY entity) {
        return beforeCreate(entity)
                .flatMap(target -> getRepository().save(target))
                .flatMap(this::afterCreate);
    }

    default Mono<ENTITY> beforeCreate(ENTITY entity) {
        return Mono.just(entity);
    }

    default Mono<ENTITY> afterCreate(ENTITY entity) {
        return Mono.just(entity);
    }

}
