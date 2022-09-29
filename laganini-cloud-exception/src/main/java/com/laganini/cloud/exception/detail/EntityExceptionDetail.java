package com.laganini.cloud.exception.detail;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.laganini.cloud.common.jackson.TypeInfoIdResolver;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityExceptionDetail<T> extends AbstractExceptionDetail {

    @JsonTypeIdResolver(TypeInfoIdResolver.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, property = "@id")
    private T id;

    public EntityExceptionDetail(String message, T id, String code) {
        super(message, code);

        this.id = id;
    }

}
