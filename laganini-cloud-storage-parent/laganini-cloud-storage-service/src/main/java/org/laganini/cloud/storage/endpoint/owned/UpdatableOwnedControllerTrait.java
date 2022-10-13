package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Owned;
import org.laganini.cloud.storage.endpoint.UpdatableControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UpdatableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        CONTEXT extends Fetchable<ID> & Owned<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends UpdateableService<ID, ENTITY> & FindableService<ID, ENTITY>,
        SUPPORT extends PersistableOwnerControllerSupport<ID, OWNER_ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends UpdatableControllerTrait<ID, ENTITY, CONTEXT, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> update(@RequestBody @Valid @NotNull CONTEXT context) {
        return getSupport()
                .getCurrent()
                .flatMap(owner -> getService()
                        .find(context.getId())
                        .filterWhen(entity -> getSupport().canUpdate(owner, entity))
                        .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(entity -> getSupport().beforeUpdate(context, entity))
                        .flatMap(entity -> getService().update(getSupport().onUpdate(
                                entity,
                                getSupport().from(context)
                        )))
                        .flatMap(getSupport()::afterUpdate)
                        .map(getSupport()::to)
                );
    }

}
