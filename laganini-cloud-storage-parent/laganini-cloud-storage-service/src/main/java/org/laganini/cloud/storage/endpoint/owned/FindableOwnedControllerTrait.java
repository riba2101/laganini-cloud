package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.model.Owner;
import org.laganini.cloud.storage.connector.service.OwnedEndpoint;
import org.laganini.cloud.storage.endpoint.FindableControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.laganini.cloud.storage.service.OwnedService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface FindableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends FindableService<ID, ENTITY> & OwnedService<ID, OWNER_ID, ENTITY>,
        SUPPORT extends FindableOwnedControllerSupport<ID, OWNER_ID, ENTITY, RESPONSE>
        >
        extends FindableControllerTrait<ID, ENTITY, RESPONSE, SERVICE, SUPPORT>, OwnedEndpoint<ID, OWNER_ID, RESPONSE>
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
