package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Mono;

public interface CreatableService<ID, ENTITY extends IdentityEntity<ID>> {

    Mono<ENTITY> create(ENTITY entity);

}
