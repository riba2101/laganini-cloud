package org.laganini.cloud.storage.connector.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Filter {

    @Valid
    @NotNull
    private final Collection<FilterCriteria<?>> filters;
    @Valid
    @NotNull
    private final Collection<SortCriteria>      sorts;
    @Valid
    @NotNull
    private final Pageable                      pageable;

    public Filter() {
        this(Collections.emptyList());
    }

    public Filter(
            Collection<FilterCriteria<?>> filters
    )
    {
        this(filters, Collections.emptyList());
    }

    public Filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts
    )
    {
        this(filters, sorts, new Pageable());
    }

    public Filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts,
            Pageable pageable
    )
    {
        this.filters = filters;
        this.sorts = sorts;
        this.pageable = pageable;
    }

}
