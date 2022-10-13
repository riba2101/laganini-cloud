package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Owned;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.PersistableService;

public interface PersistableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends PersistableService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends CreatableOwnedControllerTrait<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>,
                UpdatableOwnedControllerTrait<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

}
