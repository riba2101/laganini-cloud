package org.laganini.cloud.storage.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface PurgeableService<ID> {

    Mono<Void> markAsDeleted(ID id);

    Flux<Void> markAsDeleted(Collection<ID> ids);

    Mono<Void> unmarkAsDeleted(ID id);

    Flux<Void> unmarkAsDeleted(Collection<ID> ids);

    Mono<Void> purge(ID id);

    Flux<Void> purge(Collection<ID> ids);

}
