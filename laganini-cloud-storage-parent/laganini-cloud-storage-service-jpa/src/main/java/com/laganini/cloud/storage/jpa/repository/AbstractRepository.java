package com.laganini.cloud.storage.jpa.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract class AbstractRepository<ENTITY> {

    protected abstract JPAQuery<ENTITY> getBaseQuery();

}
