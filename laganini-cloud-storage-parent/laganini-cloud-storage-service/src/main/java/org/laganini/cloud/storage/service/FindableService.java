package org.laganini.cloud.storage.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface FindableService<ID, ENTITY> {

    Flux<ENTITY> findAll(Collection<ID> ids);

    Mono<ENTITY> find(ID id);

}
