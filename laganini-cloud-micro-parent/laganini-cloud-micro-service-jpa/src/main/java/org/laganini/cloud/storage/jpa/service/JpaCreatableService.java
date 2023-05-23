package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.laganini.cloud.storage.service.CreatableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface JpaCreatableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, CreatableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> create(ENTITY entity) {
        return beforeCreate(entity)
                .flatMap(target -> {
                    try {
                        return Mono.justOrEmpty(getRepositoryHolder().getRepository().saveAndFlush(target));
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
