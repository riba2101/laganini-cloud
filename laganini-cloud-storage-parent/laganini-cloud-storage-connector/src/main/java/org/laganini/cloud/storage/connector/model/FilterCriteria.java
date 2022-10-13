package org.laganini.cloud.storage.connector.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.laganini.cloud.common.jackson.TypeInfoIdResolver;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@SuperBuilder
public class FilterCriteria<T> {

    @NotBlank
    private final String   field;
    @NotNull
    private final Operator operator;
    @JsonTypeIdResolver(TypeInfoIdResolver.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, property = "@value")
    private final T        value;

    protected FilterCriteria() {
        this(null, null, null);
    }

    public FilterCriteria(String field, Operator operator, T value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}
