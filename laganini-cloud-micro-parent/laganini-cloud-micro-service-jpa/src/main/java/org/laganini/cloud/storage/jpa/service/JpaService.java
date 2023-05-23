package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>> {

    JpaRepositoryHolder<ID, ENTITY, REPOSITORY> getRepositoryHolder();

}
