package org.laganini.cloud.data.endpoint.owned;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.endpoint.CreatableControllerTrait;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.GlobalExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.EntityCode;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Owned;
import org.laganini.cloud.data.endpoint.owned.strategy.OwnerDecision;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.data.service.CreatableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreatableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID> & Owned<OWNER_ID>,
        SERVICE extends CreatableService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends org.laganini.cloud.data.endpoint.CreatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default RESPONSE create(@RequestBody @Valid @NotNull CONTEXT context) {
        OwnerDecision<OWNER_ID, ENTITY> ownerDecision = getSupport()
                .canCreate(context);
        if (ownerDecision == null) {
            throw getSupport()
                    .buildException(
                            ExceptionType.ENTITY_FORBIDDEN,
                            new GlobalExceptionDetail(
                                    "Create forbidden",
                                    EntityCode.FORBIDDEN.getValue()
                            )
                    );
        }

        return CreatableControllerTrait.super.create(context);
    }

}
