package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CruService<ID, ENTITY extends IdentityEntity<ID>>
        extends FilterService<ID, ENTITY>
{

    Mono<ENTITY> persist(ENTITY entity);

    Flux<ENTITY> persist(Collection<ENTITY> entities);

    Mono<ENTITY> create(ENTITY entity);

    Mono<ENTITY> refresh(ENTITY entity);

    Mono<ENTITY> update(ENTITY entity);

}
