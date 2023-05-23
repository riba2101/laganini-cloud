package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional
public interface R2dbcUpdateableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, UpdateableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> update(ENTITY entity) {
        return beforeUpdate(entity)
                .flatMap(target -> getRepositoryHolder().getRepository().save(entity))
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
