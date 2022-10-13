package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.service.UpdatableEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.laganini.cloud.storage.service.UpdateableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UpdatableControllerTrait<
        ID,
        ENTITY extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends UpdateableService<ID, ENTITY> & FindableService<ID, ENTITY>,
        SUPPORT extends PersistableControllerSupport<ID, ENTITY, CONTEXT, RESPONSE>
        >
        extends UpdatableEndpoint<ID, CONTEXT, RESPONSE>, BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> update(@RequestBody @Valid @NotNull CONTEXT context) {
        return getService()
                .find(context.getId())
                .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_NOT_FOUND)))
                .flatMap(model -> getSupport().beforeUpdate(context, model))
                .flatMap(entity -> getService().update(getSupport().onUpdate(entity, getSupport().from(context))))
                .flatMap(getSupport()::afterUpdate)
                .map(getSupport()::to);
    }

}
