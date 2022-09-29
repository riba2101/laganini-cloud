package com.laganini.cloud.storage.connector.model;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilteredAndSorted<T>
        extends Dataset<T>
{

    private Collection<FilterCriteria<?>> filterCriteria;
    private Collection<SortCriteria>      sortCriteria;

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
