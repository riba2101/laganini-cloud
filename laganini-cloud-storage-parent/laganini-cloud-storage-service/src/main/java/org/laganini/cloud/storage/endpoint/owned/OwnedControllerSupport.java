package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.endpoint.ControllerSupport;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.OwnerService;
import reactor.core.publisher.Mono;

public interface OwnedControllerSupport<ID, OWNER_ID, ENTITY extends OwnedEntity<OWNER_ID>>
        extends ControllerSupport<ID>
{

    OwnerService<OWNER_ID> getOwnerService();

    default Mono<Owner<OWNER_ID>> getCurrent() {
        return getOwnerService().getCurrent();
    }

    default boolean isSystem(Owner<OWNER_ID> owner) {
        return getOwnerService().isSystem(owner);
    }

    default boolean isAdmin(Owner<OWNER_ID> owner) {
        return getOwnerService().isAdmin(owner);
    }

    default boolean isOwner(Owner<OWNER_ID> owner, ENTITY owned) {
        return getOwnerService().isOwner(owner, owned);
    }

}
