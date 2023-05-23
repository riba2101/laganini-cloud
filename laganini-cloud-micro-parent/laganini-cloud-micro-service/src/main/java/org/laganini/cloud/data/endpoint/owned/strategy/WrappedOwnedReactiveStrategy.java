package org.laganini.cloud.data.endpoint.owned.strategy;

import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WrappedOwnedReactiveStrategy<OWNER_ID, ENTITY extends OwnedEntity<OWNER_ID>>
        implements OwnedReactiveStrategy<OWNER_ID, ENTITY>
{

    private final OwnedStrategy<OWNER_ID, ENTITY> delegate;

    public WrappedOwnedReactiveStrategy(OwnedStrategy<OWNER_ID, ENTITY> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<Boolean> supports(OWNER_ID current, ENTITY... entities) {
        return Mono.just(delegate.supports(current, entities));
    }

    @Override
    public Flux<OwnerDecision<OWNER_ID, ENTITY>> process(OWNER_ID current, ENTITY... entities) {
        return Flux.fromIterable(delegate.process(current, entities));
    }

}
