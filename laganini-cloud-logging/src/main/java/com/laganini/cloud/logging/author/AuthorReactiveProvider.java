package com.laganini.cloud.logging.author;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface AuthorReactiveProvider {

    Mono<String> provide();

}
