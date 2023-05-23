package org.laganini.cloud.observability.tracing.author;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AuthorReactiveProvider {

    Mono<String> provide();

}
