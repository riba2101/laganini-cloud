package com.laganini.cloud.storage.endpoint;

import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.storage.connector.model.*;
import com.laganini.cloud.storage.connector.service.FilterableEndpoint;
import com.laganini.cloud.storage.entity.IdentityEntity;
import com.laganini.cloud.storage.service.FilterService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractFilterController<ID, MODEL extends IdentityEntity<ID>, RESPONSE extends Fetchable<ID>, SERVICE extends FilterService<ID, MODEL>>
        extends AbstractApiController<MODEL, RESPONSE, SERVICE>
        implements FilterableEndpoint<ID, RESPONSE>
{

    protected AbstractFilterController(SERVICE service, Converter<MODEL, RESPONSE> outConverter) {
        super(service, outConverter);
    }

    @Validated
    @Override
    public Mono<FilteredAndSorted<RESPONSE>> filter(@RequestBody @Valid @NotNull Filter filter) {
        return service
                .filter(filter)
                .flatMap(this::toFiltered);
    }

    @Validated
    @Override
    public Mono<RESPONSE> find(@RequestBody @Valid @NotNull Id<ID> id) {
        return service
                .find(id.getId())
                .flatMap(this::toMono)
                .switchIfEmpty(Mono.error(buildException(ExceptionType.ENTITY_NOT_FOUND)));
    }

    @Validated
    @Override
    public Flux<RESPONSE> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return service
                .findAll(flattenIds(ids))
                .flatMap(this::toMono);
    }

    protected Map<String, FilterCriteria<?>> getFilterCriteriaByField(Filter filter) {
        return filter
                .getFilters()
                .stream()
                .collect(Collectors.toMap(FilterCriteria::getField, v -> v, (h, t) -> h));
    }

    protected <T> FilterCriteria<T> getFilterCriteria(Map<String, FilterCriteria<?>> filterCriteria, String key) {
        return (FilterCriteria<T>) filterCriteria.get(key);
    }

    protected Collection<ID> flattenIds(Collection<Id<ID>> ids) {
        return ids
                .stream()
                .map(AbstractEntityBaseContext::getId)
                .collect(Collectors.toList());
    }

    protected Collection<ID> extractIds(Collection<MODEL> entities) {
        return entities
                .stream()
                .map(IdentityEntity::getId)
                .collect(Collectors.toList());
    }

    protected Mono<FilteredAndSorted<RESPONSE>> toFiltered(FilteredAndSorted<MODEL> filtered) {
        return Mono.just(new FilteredAndSorted<>(
                toDataset(filtered.getData()),
                filtered.getCount(),
                filtered.getPage(),
                filtered.getPageSize(),
                filtered.getFilterCriteria(),
                filtered.getSortCriteria()
        ));
    }

    protected Collection<RESPONSE> toDataset(Collection<MODEL> dataset) {
//        return dataset.flatMap(this::toMono);
        return dataset
                .stream()
                .map(this::to)
                .collect(Collectors.toList());
    }

    protected Mono<RESPONSE> toMono(MODEL entity) {
        return toPartial(Mono.just(entity));
    }

    protected Mono<RESPONSE> toPartial(Mono<MODEL> entity) {
        return entity.map(this::to);
    }

}
