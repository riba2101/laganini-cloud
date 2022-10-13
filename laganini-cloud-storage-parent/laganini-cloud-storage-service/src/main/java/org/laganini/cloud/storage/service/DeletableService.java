package org.laganini.cloud.storage.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface DeletableService<ID> {

    Mono<Void> delete(ID id);

    Flux<Void> delete(Collection<ID> ids);

}
