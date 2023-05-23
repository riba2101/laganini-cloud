package org.laganini.cloud.data.endpoint.owned;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.connector.endpoint.OwnedReactiveEndpoint;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Id;
import org.laganini.cloud.data.connector.model.Owner;
import org.laganini.cloud.data.endpoint.FindableReactiveControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.data.service.FindableReactiveService;
import org.laganini.cloud.data.service.OwnedReactiveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface FindableOwnedReactiveControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends FindableReactiveService<ID, ENTITY> & OwnedReactiveService<ID, OWNER_ID, ENTITY>,
        SUPPORT extends FindableOwnedControllerSupport<ID, OWNER_ID, ENTITY, RESPONSE>
        >
        extends FindableReactiveControllerTrait<ID, ENTITY, RESPONSE, SERVICE, SUPPORT>, OwnedReactiveEndpoint<ID, OWNER_ID, RESPONSE>
{

    @Validated
    @Override
    default Mono<RESPONSE> find(@RequestBody @Valid @NotNull Id<ID> id) {
        return getSupport()
                .getCurrent()
                .flatMap(owner -> getService()
                        .find(id.getId())
                        .filterWhen(remote -> getSupport().canRead(owner, remote))
                        .map(getSupport()::to)
                        .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_NOT_FOUND)))
                );
    }

    @Validated
    @Override
    default Flux<RESPONSE> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return getSupport()
                .getCurrent()
                .flatMapMany(owner -> getService()
                        .findAll(getSupport().flattenIds(ids))
                        .filterWhen(remote -> getSupport().canRead(owner, remote))
                        .map(getSupport()::to)
                        .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_NOT_FOUND)))
                );
    }

    @Override
    default Flux<RESPONSE> findByOwner(Owner<OWNER_ID> id) {
        return getService()
                .findByOwner(id)
                .map(getSupport()::to);
    }

}
