package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Mono;

public interface OwnerService<OWNER_ID> {

    Mono<Owner<OWNER_ID>> getCurrent();

    Mono<Owner<OWNER_ID>> resolve(Owner<OWNER_ID> owner);

    <T> Mono<T> runAs(Owner<OWNER_ID> owner, Mono<T> delegate);

    boolean isSystem(Owner<OWNER_ID> owner);

    boolean isAdmin(Owner<OWNER_ID> owner);

    <T> boolean isOwner(Owner<OWNER_ID> owner, OwnedEntity<OWNER_ID> owned);

}
