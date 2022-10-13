package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.endpoint.PersistableControllerSupport;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Mono;

public interface PersistableOwnerControllerSupport<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends PersistableControllerSupport<ID, ENTITY, CONTEXT, RESPONSE>,
                OwnedControllerSupport<ID, OWNER_ID, ENTITY>
{

    default Mono<Boolean> canCreate(Owner<OWNER_ID> owner, CONTEXT remote) {
        return Mono.just(true);
    }

    default Mono<Boolean> canUpdate(Owner<OWNER_ID> owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

}
