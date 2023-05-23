package org.laganini.cloud.storage.jpa.service;

import com.querydsl.jpa.impl.JPAQuery;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
public abstract class JpaRepositoryHolder<ID, ENTITY, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>> {

    private final REPOSITORY repository;

    public JpaRepositoryHolder(REPOSITORY repository) {
        this.repository = repository;
    }

    protected REPOSITORY getRepository() {
        return repository;
    }

    public abstract JPAQuery<ENTITY> getBaseQuery(JPAQuery<?> query);

}
