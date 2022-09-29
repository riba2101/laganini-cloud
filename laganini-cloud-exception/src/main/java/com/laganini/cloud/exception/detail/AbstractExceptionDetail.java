package com.laganini.cloud.exception.detail;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.laganini.cloud.common.jackson.TypeInfoIdResolver;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonTypeIdResolver(TypeInfoIdResolver.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.CUSTOM, property = "@class")
public abstract class AbstractExceptionDetail {

    private String message;
    private String code;

    protected AbstractExceptionDetail(String message, String code) {
        this.message = message;
        this.code = code;
    }

}
