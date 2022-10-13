package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface R2dbcService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID>> {

    REPOSITORY getRepository();

}
