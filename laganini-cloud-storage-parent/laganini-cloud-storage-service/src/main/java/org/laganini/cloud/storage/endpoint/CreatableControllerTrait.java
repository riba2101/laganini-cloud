package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.service.CreatebleEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.CreatableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CreatableControllerTrait<
        ID,
        MODEL extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends CreatableService<ID, MODEL>,
        SUPPORT extends PersistableControllerSupport<ID, MODEL, CONTEXT, RESPONSE>
        >
        extends CreatebleEndpoint<ID, CONTEXT, RESPONSE>,
                BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> create(@RequestBody @Valid @NotNull CONTEXT context) {
        return getSupport()
                .beforeCreate(context)
                .map(getSupport()::from)
                .flatMap(getService()::create)
                .flatMap(getSupport()::afterCreate)
                .map(getSupport()::to);
    }

}
