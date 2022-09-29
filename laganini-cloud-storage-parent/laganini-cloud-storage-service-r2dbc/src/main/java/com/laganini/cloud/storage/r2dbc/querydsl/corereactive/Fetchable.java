package com.laganini.cloud.storage.r2dbc.querydsl.corereactive;

import com.laganini.cloud.storage.r2dbc.querydsl.corereactive.dsl.OptionalExpression;
import com.querydsl.core.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * {@code Fetchable} defines default projection methods for {@link Query} implementations.
 * All Querydsl query implementations should implement this interface.
 *
 * @param <T> result type
 */
public interface Fetchable<T> {

    /**
     * Get the projection as a typed Flux.
     * Consuming the result produces {@link NullPointerException} if a value is null.
     *
     * @return result
     * @see OptionalExpression
     */
    Flux<T> fetch();

    /**
     * Get the first result of the projection.
     * Consuming the result produces {@link NullPointerException} if the value is null.
     *
     * @return first result
     * @see OptionalExpression
     */
    Mono<T> fetchFirst();

    /**
     * Get the projection as a unique result.
     * Consuming the result produces {@link NullPointerException} if the value is null.
     * Consuming the result produces {@link IndexOutOfBoundsException} if there is more than one matching result
     *
     * @return first result
     * @see OptionalExpression
     */
    Mono<T> fetchOne();

}
