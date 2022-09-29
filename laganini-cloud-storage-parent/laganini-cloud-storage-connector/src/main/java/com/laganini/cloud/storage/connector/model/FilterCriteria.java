package com.laganini.cloud.storage.connector.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.laganini.cloud.common.jackson.TypeInfoIdResolver;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class FilterCriteria<T> {

    @NotBlank
    private String   field;
    @NotNull
    private Operator operator;
    @JsonTypeIdResolver(TypeInfoIdResolver.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, property = "@value")
    private T        value;

    public FilterCriteria(String field, Operator operator, T value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }
}
