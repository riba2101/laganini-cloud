package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@ToString
@EqualsAndHashCode
public class Pageable {

    public static final int DEFAULT_PAGE      = 1;
    public static final int DEFAULT_PAGE_SIZE = 1000;

    @Min(1)
    private final int page;
    @Min(1)
    @Max(10000)
    private final int pageSize;

    public Pageable() {
        this(DEFAULT_PAGE, DEFAULT_PAGE_SIZE);
    }

    public Pageable(
            int page,
            int pageSize
    )
    {
        this.page = page;
        this.pageSize = pageSize;
    }

}
