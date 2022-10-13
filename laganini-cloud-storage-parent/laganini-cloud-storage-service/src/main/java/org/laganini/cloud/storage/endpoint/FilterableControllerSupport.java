package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilterCriteria;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public interface FilterableControllerSupport<
        ID,
        ENTITY extends IdentityEntity<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends FindableControllerSupport<ID, ENTITY, RESPONSE>
{

    default FilteredAndSorted<RESPONSE> to(FilteredAndSorted<ENTITY> filtered) {
        return new FilteredAndSorted<>(
                to(filtered.getData()),
                filtered.getCount(),
                filtered.getPage(),
                filtered.getPageSize(),
                filtered.getFilterCriteria(),
                filtered.getSortCriteria()
        );
    }

    default Collection<RESPONSE> to(Collection<ENTITY> dataset) {
        return dataset
                .stream()
                .map(this::to)
                .collect(Collectors.toList());
    }

    default Map<String, FilterCriteria<?>> getByField(Filter filter) {
        return filter
                .getFilters()
                .stream()
                .collect(Collectors.toMap(FilterCriteria::getField, v -> v, (h, t) -> h));
    }

    default <T> FilterCriteria<T> find(Map<String, FilterCriteria<?>> filterCriteria, String key) {
        return (FilterCriteria<T>) filterCriteria.get(key);
    }

}
