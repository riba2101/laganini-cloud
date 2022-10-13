package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.Collections;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class Dataset<T> {

    private final Collection<T> data;
    private final long          count;
    private final int           page;
    private final int           pageSize;

    protected Dataset() {
        this(Collections.emptyList(), 0, 0, 0);
    }

    public Dataset(Collection<T> data, long count, int page, int pageSize) {
        this.data = data;
        this.count = count;
        this.page = page;
        this.pageSize = pageSize;
    }
}
