package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Collections;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class FilteredAndSorted<T>
        extends Dataset<T>
{

    private final Collection<FilterCriteria<?>> filterCriteria;
    private final Collection<SortCriteria>      sortCriteria;

    protected FilteredAndSorted() {
        this(
                Collections.emptyList(),
                0,
                0,
                0,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public FilteredAndSorted(
            Collection<T> data,
            long total,
            int page,
            int pageSize,
            Collection<FilterCriteria<?>> filterCriteria,
            Collection<SortCriteria> sortCriteria
    )
    {
        super(data, total, page, pageSize);

        this.filterCriteria = filterCriteria;
        this.sortCriteria = sortCriteria;
    }

}
