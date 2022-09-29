package org.laganini.cloud.exception.detail.code.registry;

import lombok.Getter;

public enum EntityCode {

    FORBIDDEN("Forbidden"),
    NOT_FOUND("NotFound"),
    ;

    @Getter
    private final String value;

    EntityCode(String value) {
        this.value = value;
    }

}
