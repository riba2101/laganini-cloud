package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.PersistableService;

public interface PersistableControllerTrait<
        ID,
        ENTITY extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends PersistableService<ID, ENTITY>,
        SUPPORT extends PersistableControllerSupport<ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends CreatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>,
                UpdatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

}
