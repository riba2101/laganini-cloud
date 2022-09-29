package com.laganini.cloud.storage.connector.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class Pageable {

    @Min(0)
    private int page;
    @Min(0)
    @Max(10000)
    private int pageSize;

    public Pageable(
            int page,
            int pageSize
    )
    {
        this.page = page;
        this.pageSize = pageSize;
    }

}
