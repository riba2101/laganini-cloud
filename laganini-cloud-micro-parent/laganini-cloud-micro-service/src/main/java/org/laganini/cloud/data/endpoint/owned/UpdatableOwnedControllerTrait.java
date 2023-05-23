package org.laganini.cloud.data.endpoint.owned;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.endpoint.UpdatableControllerTrait;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.GlobalExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.EntityCode;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Owned;
import org.laganini.cloud.data.endpoint.owned.strategy.OwnerDecision;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.data.service.FindableService;
import org.laganini.cloud.data.service.UpdateableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface UpdatableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends UpdateableService<ID, ENTITY> & FindableService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends org.laganini.cloud.data.endpoint.UpdatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default RESPONSE update(@RequestBody @Valid @NotNull CONTEXT context) {
        ENTITY entity = getService()
                .find(context.getId());

        OwnerDecision<OWNER_ID> ownerDecision = getSupport()
                .canUpdate(entity);
        if (ownerDecision == null) {
            throw getSupport().buildException(
                    ExceptionType.ENTITY_FORBIDDEN,
                    new GlobalExceptionDetail("Update owner decision missing", EntityCode.FORBIDDEN.getValue())
            );
        }

        return UpdatableControllerTrait.super.update(context);
    }

}
