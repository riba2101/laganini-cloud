package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import com.querydsl.sql.SQLQuery;

import java.util.function.Function;

public class R2dbcRepositoryHolderFactory {

    public <ID, ENTITY, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>> R2dbcRepositoryHolder<ID, ENTITY, REPOSITORY> build(
            REPOSITORY repository,
            Function<SQLQuery<?>, SQLQuery<ENTITY>> function
    )
    {
        return new R2dbcRepositoryHolder<>(repository) {
            @Override
            protected SQLQuery<ENTITY> getBaseQuery(SQLQuery<?> query) {
                return function.apply(query);
            }
        };
    }

}
