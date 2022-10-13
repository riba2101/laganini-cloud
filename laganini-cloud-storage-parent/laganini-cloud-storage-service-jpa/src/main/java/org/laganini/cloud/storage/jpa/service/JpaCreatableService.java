package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.CreatableService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface JpaCreatableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, CreatableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> create(ENTITY entity) {
        return beforeCreate(entity)
                .flatMap(target -> {
                    try {
                        return Mono.justOrEmpty(getRepository().saveAndFlush(target));
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(this::afterCreate);
    }

    default Mono<ENTITY> beforeCreate(ENTITY entity) {
        return Mono.just(entity);
    }

    default Mono<ENTITY> afterCreate(ENTITY entity) {
        return Mono.just(entity);
    }

}
