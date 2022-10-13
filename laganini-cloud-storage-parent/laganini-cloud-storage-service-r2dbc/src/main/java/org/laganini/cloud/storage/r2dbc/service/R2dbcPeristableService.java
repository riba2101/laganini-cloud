package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.PersistableService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional
public interface R2dbcPeristableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID>>
        extends R2dbcCreatableService<ID, ENTITY, REPOSITORY>,
                R2dbcUpdateableService<ID, ENTITY, REPOSITORY>,
                PersistableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> persist(ENTITY entity) {
        return getRepository().save(entity);
    }

    @Override
    default Flux<ENTITY> persist(Collection<ENTITY> entities) {
        return getRepository().saveAll(entities);
    }

}
