package com.laganini.cloud.storage.r2dbc.querydsl.corereactive;

import com.querydsl.core.ResultTransformer;
import com.querydsl.core.SimpleQuery;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;

/**
 * {@code FetchableQuery} extends {@link Fetchable} and {@link SimpleQuery} with projection changing
 * methods and result aggregation functionality using {@link ResultTransformer} instances.
 *
 * @param <T> element type
 * @param <Q> concrete subtype
 */
public interface FetchableR2dbcQuery<T, Q extends FetchableR2dbcQuery<T, Q>>
        extends SimpleQuery<Q>, Fetchable<T>
{

    /**
     * Change the projection of this query
     *
     * @param <U>
     * @param expr new projection
     * @return the current object
     */
    <U> FetchableR2dbcQuery<U, ?> select(Expression<U> expr);

    /**
     * Change the projection of this query
     *
     * @param exprs new projection
     * @return the current object
     */
    FetchableR2dbcQuery<Tuple, ?> select(Expression<?>... exprs);

    /**
     * Apply the given transformer to this {@code FetchableQuery} instance and return the results
     *
     * @param <S>
     * @param transformer result transformer
     * @return transformed result
     */
    <S> S transform(ResultTransformer<S> transformer);

}

