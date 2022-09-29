package com.laganini.cloud.storage.endpoint;

import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.storage.connector.model.*;
import com.laganini.cloud.storage.connector.service.PurgeableEndpoint;
import com.laganini.cloud.storage.dto.Owner;
import com.laganini.cloud.storage.entity.PurgeableEntity;
import com.laganini.cloud.storage.service.OwnerService;
import com.laganini.cloud.storage.service.PurgeableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Slf4j
public abstract class AbstractPurgeableController<ID extends Serializable, ENTITY extends PurgeableEntity<ID>, CONTEXT extends AbstractPurgeableContext<ID>,
        RESPONSE extends AbstractPurgeableBase<ID>, SERVICE extends PurgeableService<ID, ENTITY>>
        extends AbstractOwnedController<ID, ENTITY, CONTEXT, RESPONSE, SERVICE>
        implements PurgeableEndpoint<ID, CONTEXT, RESPONSE>
{

    public AbstractPurgeableController(
            SERVICE service,
            Converter<CONTEXT, ENTITY> inConverter,
            Converter<ENTITY, RESPONSE> outConverter,
            OwnerService ownerService
    )
    {
        super(service, inConverter, outConverter, ownerService);
    }

    @Validated
    @Override
    public Mono<Void> markAsDeleted(@RequestBody @NotNull @Valid Id<ID> id) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .find(id.getId())
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(item -> service.markAsDeleted(id.getId())));
    }

    @Validated
    @Override
    public Mono<Void> markAsDeleted(@RequestBody @NotNull @Valid Collection<Id<ID>> ids) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .findAll(flattenIds(ids))
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .collectList()
                        .flatMap(items -> service.markAsDeleted(extractIds(items)))
                );
    }

    @Validated
    @Override
    public Mono<Void> unmarkAsDeleted(@RequestBody @NotNull @Valid Id<ID> id) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .find(id.getId())
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(item -> service.markAsDeleted(id.getId())));
    }

    @Validated
    @Override
    public Mono<Void> unmarkAsDeleted(@RequestBody @NotNull @Valid Collection<Id<ID>> ids) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .findAll(flattenIds(ids))
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .collectList()
                        .flatMap(items -> service.markAsDeleted(extractIds(items))));
    }

    @Validated
    @Override
    public Mono<Void> purge(@RequestBody @Valid @NotNull Id<ID> id) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .find(id.getId())
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(item -> service.purge(id.getId())));
    }

    @Validated
    @Override
    public Mono<Void> purge(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .findAll(flattenIds(ids))
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .collectList()
                        .flatMap(items -> service.purge(extractIds(items))));
    }

    @Override
    protected Collection<RESPONSE> toDataset(Owner owner, Collection<ENTITY> dataset) {
        return toDataset(dataset);
    }

    @Override
    protected ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setDeletedAt(before.getDeletedAt());

        return super.onUpdate(before, after);
    }

    @Override
    protected Mono<Filter> adjustFilter(Owner owner, Filter filter) {
        return getPurgeAdjustedFilter(owner, filter);
    }

    protected Mono<Filter> getPurgeAdjustedFilter(Owner owner, Filter filter) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(filter);
        }

        Map<String, FilterCriteria<?>> filterCriteriaByField = getFilterCriteriaByField(filter);

        filterCriteriaByField.put(
                PurgeableEndpoint.DELETED_AT,
                new FilterCriteria<>(PurgeableEndpoint.DELETED_AT, Operator.EQUALS, null)
        );

        filter.setFilters(filterCriteriaByField.values());

        return super.adjustFilter(owner, filter);
    }

    @Override
    protected Mono<Boolean> canRead(Owner owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (entity.getDeletedAt() != null) {
            return Mono.just(false);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

    @Override
    protected Mono<Boolean> canModify(Owner owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (entity.getDeletedAt() != null) {
            return Mono.just(false);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

}
