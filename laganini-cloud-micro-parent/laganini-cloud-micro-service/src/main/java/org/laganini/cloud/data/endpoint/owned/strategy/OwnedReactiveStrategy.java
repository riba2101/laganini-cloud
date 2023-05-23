package org.laganini.cloud.data.endpoint.owned.strategy;

import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OwnedReactiveStrategy<OWNER_ID, ENTITY extends OwnedEntity<OWNER_ID>> {

    Mono<Boolean> supports(OWNER_ID current, ENTITY... entities);

    Flux<OwnerDecision<OWNER_ID, ENTITY>> process(OWNER_ID current, ENTITY... entities);

}
