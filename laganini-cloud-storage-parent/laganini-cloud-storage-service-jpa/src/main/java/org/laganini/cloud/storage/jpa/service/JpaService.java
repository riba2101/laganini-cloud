package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface JpaService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID>> {

    REPOSITORY getRepository();

}
