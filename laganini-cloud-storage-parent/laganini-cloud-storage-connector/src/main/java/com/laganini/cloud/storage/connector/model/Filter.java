package com.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Filter {

    @Valid
    @NotNull
    private Collection<FilterCriteria<?>> filters;
    @Valid
    @NotNull
    private Collection<SortCriteria>      sorts;
    @Valid
    @NotNull
    private Pageable                      pageable;

    public Filter() {
        this(Collections.emptyList(), Collections.emptyList(), new Pageable(0, 100));
    }

    public Filter(
            Collection<FilterCriteria<?>> filters
    )
    {
        this(filters, Collections.emptyList(), new Pageable(0, 100));
    }

    public Filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts
    )
    {
        this(filters, sorts, new Pageable(0, 100));
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
