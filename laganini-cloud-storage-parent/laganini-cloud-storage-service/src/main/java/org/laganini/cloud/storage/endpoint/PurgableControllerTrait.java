package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.AbstractPurgeableBase;
import org.laganini.cloud.storage.connector.model.AbstractPurgeableContext;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.service.PurgableEndpoint;
import org.laganini.cloud.storage.entity.PurgeableEntity;
import org.laganini.cloud.storage.service.PurgeableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface PurgableControllerTrait<
        ID,
        ENTITY extends PurgeableEntity<ID>,
        CONTEXT extends AbstractPurgeableContext<ID>,
        RESPONSE extends AbstractPurgeableBase<ID>,
        SERVICE extends PurgeableService<ID>,
        SUPPORT extends DeletableControllerSupport<ID>
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
