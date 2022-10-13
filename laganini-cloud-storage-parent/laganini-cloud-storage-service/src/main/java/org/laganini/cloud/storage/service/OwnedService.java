package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Flux;

public interface OwnedService<ID, OWNER_ID, ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>> {

    Flux<ENTITY> findByOwner(Owner<OWNER_ID> id);

}
