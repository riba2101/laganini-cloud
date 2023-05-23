package org.laganini.cloud.data.endpoint.owned;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.GlobalExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.EntityCode;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Owned;
import org.laganini.cloud.data.endpoint.UpdatableReactiveControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.data.service.FindableReactiveService;
import org.laganini.cloud.data.service.UpdateableReactiveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface UpdatableOwnedReactiveControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends UpdateableReactiveService<ID, ENTITY> & FindableReactiveService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerReactiveControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends UpdatableReactiveControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> update(@RequestBody @Valid @NotNull CONTEXT context) {
        return getService()
                .find(context.getId())
                .flatMap(existing -> getSupport()
                        .canUpdate(existing)
                        .switchIfEmpty(Mono.error(getSupport().buildException(
                                ExceptionType.ENTITY_FORBIDDEN,
                                new GlobalExceptionDetail(
                                        "No update owner strategy found",
                                        EntityCode.FORBIDDEN.getValue()
                                )
                        )))
                        .flatMap(ownerDecision -> {
                            context.setOwner(ownerDecision.getOwner());

                            return UpdatableReactiveControllerTrait.super.update(context);
                        })
                );
    }

}
