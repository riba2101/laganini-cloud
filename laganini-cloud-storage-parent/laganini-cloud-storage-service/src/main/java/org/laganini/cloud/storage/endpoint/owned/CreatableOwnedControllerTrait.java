package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Owned;
import org.laganini.cloud.storage.endpoint.CreatableControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.CreatableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreatableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends CreatableService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends CreatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> create(@RequestBody @Valid @NotNull CONTEXT context) {
        return getSupport()
                .getCurrent()
                .filterWhen(owner -> getSupport().canCreate(owner, context))
                .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_FORBIDDEN)))
                .flatMap(owner -> {
                    if (context.getOwner() == null) {
                        context.setOwner(owner);
                    }

                    return CreatableControllerTrait.super.create(context);
                });
    }

}
