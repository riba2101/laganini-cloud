package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Mono;

public interface UpdateableService<ID, ENTITY extends IdentityEntity<ID>> {

    Mono<ENTITY> update(ENTITY entity);

}
