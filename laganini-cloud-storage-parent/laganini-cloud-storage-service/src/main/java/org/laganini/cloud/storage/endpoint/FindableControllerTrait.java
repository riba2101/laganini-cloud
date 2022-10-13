package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.service.FindableEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface FindableControllerTrait<
        ID,
        ENTITY extends IdentityEntity<ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends FindableService<ID, ENTITY>,
        SUPPORT extends FindableControllerSupport<ID, ENTITY, RESPONSE>
        >
        extends FindableEndpoint<ID, RESPONSE>, BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<RESPONSE> find(@RequestBody @Valid @NotNull Id<ID> id) {
        return getService()
                .find(id.getId())
                .map(getSupport()::to)
                .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_NOT_FOUND)));
    }

    @Validated
    @Override
    default Flux<RESPONSE> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return getService()
                .findAll(getSupport().flattenIds(ids))
                .map(getSupport()::to);
    }

}
