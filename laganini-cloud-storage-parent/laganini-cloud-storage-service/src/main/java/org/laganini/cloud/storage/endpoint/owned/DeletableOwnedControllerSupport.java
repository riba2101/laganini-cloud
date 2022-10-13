package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.endpoint.DeletableControllerSupport;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Mono;

public interface DeletableOwnedControllerSupport<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>
        >
        extends DeletableControllerSupport<ID>,
                OwnedControllerSupport<ID, OWNER_ID, ENTITY>
{

    default Mono<Boolean> canDelete(Owner<OWNER_ID> owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

}
