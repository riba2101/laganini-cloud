package org.laganini.cloud.storage.repository;

import org.laganini.cloud.storage.connector.model.FilterCriteria;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.connector.model.SortCriteria;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;

@NoRepositoryBean
public interface FilterRepository<ENTITY> {

    FilteredAndSorted<ENTITY> filter(Collection<FilterCriteria<?>> filters);

    FilteredAndSorted<ENTITY> filter(Collection<FilterCriteria<?>> filters, Collection<SortCriteria> sorts);

    FilteredAndSorted<ENTITY> filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts,
            int page,
            int pageSize
    );

}
