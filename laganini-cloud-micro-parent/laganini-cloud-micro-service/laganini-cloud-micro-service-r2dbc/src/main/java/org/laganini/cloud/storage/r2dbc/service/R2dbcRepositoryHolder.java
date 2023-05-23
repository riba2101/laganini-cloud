package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import com.querydsl.sql.SQLQuery;

public abstract class R2dbcRepositoryHolder<ID, ENTITY, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>> {

    private final REPOSITORY repository;

    public R2dbcRepositoryHolder(REPOSITORY repository) {
        this.repository = repository;
    }

    protected REPOSITORY getRepository() {
        return repository;
    }

    protected abstract SQLQuery<ENTITY> getBaseQuery(SQLQuery<?> query);

}
