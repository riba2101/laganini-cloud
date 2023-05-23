package org.laganini.cloud.data.service;

import org.laganini.cloud.storage.entity.IdentityEntity;

public interface UpdateableService<ID, ENTITY extends IdentityEntity<ID>> {

    ENTITY update(ENTITY entity);

}
