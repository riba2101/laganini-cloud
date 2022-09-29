package com.laganini.cloud.storage.connector.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class SortCriteria {

    @NotBlank
    private String    field;
    @NotNull
    private Direction direction;

    public SortCriteria(
            String field,
            Direction direction
    )
    {
        this.field = field;
        this.direction = direction;
    }

}
