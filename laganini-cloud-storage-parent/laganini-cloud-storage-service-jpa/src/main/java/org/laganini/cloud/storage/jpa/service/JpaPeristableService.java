package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.PersistableService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional
public interface JpaPeristableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID>>
        extends JpaCreatableService<ID, ENTITY, REPOSITORY>,
                JpaUpdateableService<ID, ENTITY, REPOSITORY>,
                PersistableService<ID, ENTITY>
{

    @Override
    default Mono<ENTITY> persist(ENTITY entity) {
        try {
            return Mono.justOrEmpty(getRepository().saveAndFlush(entity));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    default Flux<ENTITY> persist(Collection<ENTITY> entities) {
        try {
            return Flux.fromIterable(getRepository().saveAllAndFlush(entities));
        } catch (Exception e) {
            return Flux.error(e);
        }
    }

}
