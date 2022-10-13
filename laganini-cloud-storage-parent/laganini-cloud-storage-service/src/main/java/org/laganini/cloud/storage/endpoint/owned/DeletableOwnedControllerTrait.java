package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.endpoint.DeletableControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.DeletableService;
import org.laganini.cloud.storage.service.FindableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface DeletableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        SERVICE extends DeletableService<ID> & FindableService<ID, ENTITY>,
        SUPPORT extends DeletableOwnedControllerSupport<ID, OWNER_ID, ENTITY>
        >
        extends DeletableControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id) {
        return getSupport()
                .getCurrent()
                .flatMap(owner -> getService()
                        .find(id.getId())
                        .filterWhen(entity -> getSupport().canDelete(owner, entity))
                        .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(entity -> DeletableControllerTrait.super.delete(id))
                );
    }

    @Validated
    @Override
    default Flux<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return getSupport()
                .getCurrent()
                .flatMapMany(owner -> getService()
                        .findAll(getSupport().flattenIds(ids))
                        .filterWhen(remote -> getSupport().canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(getSupport().buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .collectList()
                        .flatMapMany(items -> DeletableControllerTrait.super.delete(ids))
                );
    }

}
