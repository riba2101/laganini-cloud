package org.laganini.cloud.storage.jpa.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;

import java.util.function.Function;

public class JpaRepositoryHolderFactory {

    public static <ID, ENTITY, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>> JpaRepositoryHolder<ID, ENTITY, REPOSITORY> build(
            REPOSITORY repository,
            Function<JPAQuery<?>, JPAQuery<ENTITY>> baseQueryBuilder
    )
    {
        return new JpaRepositoryHolder<>(repository) {
            @Override
            public JPAQuery<ENTITY> getBaseQuery(JPAQuery<?> query) {
                return baseQueryBuilder.apply(query);
            }
        };
    }

}
