package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@EqualsAndHashCode
public class SortCriteria {

    @NotBlank
    private final String    field;
    @NotNull
    private final Direction direction;

    protected SortCriteria() {
        this(null, null);
    }

    public SortCriteria(
            String field,
            Direction direction
    )
    {
        this.field = field;
        this.direction = direction;
    }

}
