package org.laganini.cloud.data.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface FindableReactiveService<ID, ENTITY> {

    Flux<ENTITY> findAll(Collection<ID> ids);

    Mono<ENTITY> find(ID id);

}
