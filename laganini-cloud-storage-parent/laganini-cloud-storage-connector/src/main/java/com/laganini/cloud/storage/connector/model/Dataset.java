package com.laganini.cloud.storage.connector.model;

import lombok.*;

import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dataset<T> {

    private Collection<T> data;
    private long          count;
    private int           page;
    private int           pageSize;

    public Dataset(Collection<T> data, long count, int page, int pageSize) {
        this.data = data;
        this.count = count;
        this.page = page;
        this.pageSize = pageSize;
    }
}
