package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface JpaUpdateableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, UpdateableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> update(ENTITY entity) {
        return beforeUpdate(entity)
                .flatMap(target -> {
                    try {
                        return Mono.justOrEmpty(getRepository().saveAndFlush(onUpdate(target, entity)));
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

    default ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setId(before.getId());

        return after;
    }

}
