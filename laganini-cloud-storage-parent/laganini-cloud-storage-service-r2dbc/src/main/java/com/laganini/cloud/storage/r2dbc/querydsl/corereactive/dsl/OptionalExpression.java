package com.laganini.cloud.storage.r2dbc.querydsl.corereactive.dsl;

import com.laganini.cloud.storage.r2dbc.querydsl.corereactive.Fetchable;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Visitor;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * Optional expression to support null result for
 * {@link reactor.core.publisher.Mono} and {@link reactor.core.publisher.Flux}.
 *
 * @see Fetchable
 */
public final class OptionalExpression<T>
        implements Expression<Optional<T>>
{

    private final Expression<T> wrapped;

    private OptionalExpression(Expression<T> wrapped) {
        this.wrapped = Objects.requireNonNull(wrapped);
    }

    public static <T> OptionalExpression<T> of(Expression<T> expr) {
        return new OptionalExpression<>(expr);
    }

    @Nullable
    @Override
    public <R, C> R accept(Visitor<R, C> v, @Nullable C context) {
        return wrapped.accept(v, context);
    }

    @Override
    public Class<? extends Optional<T>> getType() {
        throw new UnsupportedOperationException("Generic optional type is not supported");
    }

    public Class<? extends T> getWrappedType() {
        return wrapped.getType();
    }

}
