package org.laganini.cloud.storage.connector.model;

import lombok.Getter;

@Getter
public enum Operator {

    EQUALS,
    NOT_EQUALS,
    GT,
    GTE,
    LT,
    LTE,
    CONTAINS,
    STARTS_WITH,
    ENDS_WITH,

}
