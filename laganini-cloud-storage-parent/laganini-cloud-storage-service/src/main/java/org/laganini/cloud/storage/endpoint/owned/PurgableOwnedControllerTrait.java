package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.service.PurgableEndpoint;
import org.laganini.cloud.storage.endpoint.BaseControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.PurgeableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface PurgableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        SERVICE extends PurgeableService<ID>,
        SUPPORT extends DeletableOwnedControllerSupport<ID, OWNER_ID, ENTITY>
        >
        extends PurgableEndpoint<ID>, BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<Void> markAsDeleted(@RequestBody @NotNull @Valid Id<ID> id) {
        return getService().markAsDeleted(id.getId());
    }

    @Validated
    @Override
    default Flux<Void> markAsDeleted(@RequestBody @NotNull @Valid Collection<Id<ID>> ids) {
        return getService().markAsDeleted(getSupport().flattenIds(ids));
    }

    @Validated
    @Override
    default Mono<Void> unmarkAsDeleted(@RequestBody @NotNull @Valid Id<ID> id) {
        return getService().markAsDeleted(id.getId());
    }

    @Validated
    @Override
    default Flux<Void> unmarkAsDeleted(@RequestBody @NotNull @Valid Collection<Id<ID>> ids) {
        return getService().unmarkAsDeleted(getSupport().flattenIds(ids));
    }

    @Validated
    @Override
    default Mono<Void> purge(@RequestBody @Valid @NotNull Id<ID> id) {
        return getService().purge(id.getId());
    }

    @Validated
    @Override
    default Flux<Void> purge(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return getService().purge(getSupport().flattenIds(ids));
    }

}
