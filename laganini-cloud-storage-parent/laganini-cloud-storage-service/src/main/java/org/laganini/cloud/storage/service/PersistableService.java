package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface PersistableService<ID, ENTITY extends IdentityEntity<ID>>
        extends CreatableService<ID, ENTITY>, UpdateableService<ID, ENTITY>, FindableService<ID, ENTITY>
{

    Mono<ENTITY> persist(ENTITY entity);

    Flux<ENTITY> persist(Collection<ENTITY> entities);

}
