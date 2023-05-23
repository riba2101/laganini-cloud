package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface R2dbcService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>> {


    R2dbcRepositoryHolder<ID, ENTITY, REPOSITORY> getRepositoryHolder();

}
