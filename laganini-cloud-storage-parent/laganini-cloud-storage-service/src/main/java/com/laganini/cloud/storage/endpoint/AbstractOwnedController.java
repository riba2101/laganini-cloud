package com.laganini.cloud.storage.endpoint;

import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.storage.connector.model.*;
import com.laganini.cloud.storage.connector.service.OwnedEndpoint;
import com.laganini.cloud.storage.dto.Owner;
import com.laganini.cloud.storage.entity.OwnedEntity;
import com.laganini.cloud.storage.service.OwnedService;
import com.laganini.cloud.storage.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Slf4j
public abstract class AbstractOwnedController<ID extends Serializable, ENTITY extends OwnedEntity<ID>, CONTEXT extends AbstractOwnedContext<ID>,
        RESPONSE extends AbstractOwnedBase<ID>, SERVICE extends OwnedService<ID, ENTITY>>
        extends AbstractCRUDController<ID, ENTITY, CONTEXT, RESPONSE, SERVICE>
        implements OwnedEndpoint<ID, CONTEXT, RESPONSE>
{

    protected final OwnerService ownerService;

    public AbstractOwnedController(
            SERVICE service,
            Converter<CONTEXT, ENTITY> inConverter,
            Converter<ENTITY, RESPONSE> outConverter,
            OwnerService ownerService
    )
    {
        super(service, inConverter, outConverter);

        this.ownerService = ownerService;
    }

    @Validated
    @Override
    public Mono<FilteredAndSorted<RESPONSE>> filter(@RequestBody @Valid @NotNull Filter filter) {
        return ownerService
                .resolve()
                .flatMap(owner -> this
                        .adjustFilter(owner, filter)
                        .flatMap(adjustedFilter -> service
                                .filter(adjustedFilter)
                                .flatMap(filtered -> toFiltered(owner, filtered))));
    }

    @Validated
    @Override
    public Flux<RESPONSE> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return ownerService
                .resolve()
                .flatMapMany(owner -> service
                        .findAll(flattenIds(ids))
                        .filterWhen(remote -> canRead(owner, remote))
                        .flatMap(this::toMono));
    }

    @Validated
    @Override
    public Mono<RESPONSE> find(@RequestBody @NotNull @Valid Id<ID> id) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .find(id.getId())
                        .filterWhen(remote -> canRead(owner, remote))
                        .flatMap(this::toMono)
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN))));
    }

    @Override
    public Flux<RESPONSE> findByOwnerId(@Valid @NotNull Id<Long> id) {
        return ownerService
                .resolve()
                .flatMapMany(owner -> service
                        .findByOwnerId(id.getId())
                        .filterWhen(remote -> canRead(owner, remote))
                        .flatMap(this::toMono)
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN))));
    }

    @Validated
    @Override
    public Mono<RESPONSE> create(@RequestBody @NotNull @Valid CONTEXT context) {
        return ownerService
                .resolve()
                .flatMap(owner -> {
                    if (context.getOwnerId() == null) {
                        context.setOwnerId(owner.getOwnerId());
                    }

                    return super.create(context);
                });
    }

    @Validated
    @Override
    public Mono<RESPONSE> update(@RequestBody @NotNull @Valid CONTEXT context) {
        return ownerService
                .resolve()
                .flatMap(owner -> {
                    if (context.getOwnerId() == null) {
                        context.setOwnerId(owner.getOwnerId());
                    }

                    return super.update(context);
                });
    }

    @Validated
    @Override
    public Mono<Void> delete(@RequestBody @NotNull @Valid Id<ID> id) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .find(id.getId())
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .flatMap(item -> super.delete(id))
                );
    }

    @Validated
    @Override
    public Mono<Void> delete(@RequestBody @NotNull @Valid Collection<Id<ID>> ids) {
        return ownerService
                .resolve()
                .flatMap(owner -> service
                        .findAll(flattenIds(ids))
                        .filterWhen(remote -> canDelete(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN)))
                        .collectList()
                        .flatMap(items -> service.delete(extractIds(items)))
                );
    }

    @Override
    protected Mono<CONTEXT> beforeCreate(CONTEXT context) {
        return ownerService
                .resolve()
                .flatMap(owner -> super
                        .beforeCreate(context)
                        .filterWhen(remote -> canCreate(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN))));
    }

    @Override
    protected Mono<ENTITY> beforeUpdate(CONTEXT context, ENTITY entity) {
        return ownerService
                .resolve()
                .flatMap(owner -> super
                        .beforeUpdate(context, entity)
                        .filterWhen(remote -> canUpdate(owner, remote))
                        .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_FORBIDDEN))));
    }

    @Override
    protected ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setOwnerId(before.getOwnerId());

        return super.onUpdate(before, after);
    }

    protected Mono<FilteredAndSorted<RESPONSE>> toFiltered(Owner owner, FilteredAndSorted<ENTITY> filtered) {
        return Mono.just(new FilteredAndSorted<>(
                toDataset(owner, filtered.getData()),
                filtered.getCount(),
                filtered.getPage(),
                filtered.getPageSize(),
                filtered.getFilterCriteria(),
                filtered.getSortCriteria()
        ));
    }

    protected Collection<RESPONSE> toDataset(Owner owner, Collection<ENTITY> dataset) {
        return toDataset(dataset);
    }

    protected Mono<Filter> adjustFilter(Owner owner, Filter filter) {
        return getOwnerAdjustedFilter(owner, filter);
    }

    protected Mono<Filter> getOwnerAdjustedFilter(Owner owner, Filter filter) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(filter);
        }

        Map<String, FilterCriteria<?>> filterCriteriaByField = getFilterCriteriaByField(filter);

        filterCriteriaByField.put(
                OwnedEndpoint.OWNER_ID,
                new FilterCriteria<>(OwnedEndpoint.OWNER_ID, Operator.EQUALS, owner.getOwnerId())
        );

        filter.setFilters(filterCriteriaByField.values());

        return Mono.just(filter);
    }

    protected Mono<Boolean> canRead(Owner owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

    protected Mono<Boolean> canCreate(Owner owner, CONTEXT remote) {
        return Mono.just(true);
    }

    protected Mono<Boolean> canUpdate(Owner owner, ENTITY entity) {
        return canModify(owner, entity);
    }

    protected Mono<Boolean> canDelete(Owner owner, ENTITY entity) {
        return canModify(owner, entity);
    }

    protected Mono<Boolean> canModify(Owner owner, ENTITY entity) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(true);
        }

        if (isOwner(owner, entity)) {
            return Mono.just(true);
        }

        return Mono.just(false);
    }

    protected boolean isSystem(Owner owner) {
        return ownerService.isSystem(owner);
    }

    protected boolean isAdmin(Owner owner) {
        return ownerService.isAdmin(owner);
    }

    protected boolean isOwner(Owner owner, OwnedEntity<?> owned) {
        return ownerService.isOwner(owner, owned);
    }

}
