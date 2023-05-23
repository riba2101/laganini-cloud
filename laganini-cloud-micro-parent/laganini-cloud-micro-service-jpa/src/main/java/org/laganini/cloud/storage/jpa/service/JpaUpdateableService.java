package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface JpaUpdateableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, UpdateableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> update(ENTITY entity) {
        return beforeUpdate(entity)
                .flatMap(target -> {
                    try {
                        return Mono
                                .justOrEmpty(getRepositoryHolder().getRepository().findById(target.getId()))
                                .map(existing -> getRepositoryHolder()
                                        .getRepository()
                                        .saveAndFlush(onUpdate(existing, target))
                                );
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                })
                .flatMap(this::afterUpdate);
    }

    default Mono<ENTITY> beforeUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    default Mono<ENTITY> afterUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    default ENTITY onUpdate(ENTITY existing, ENTITY target) {
        target.setId(existing.getId());
        BeanUtils.copyProperties(target, existing);

        return existing;
    }

}
