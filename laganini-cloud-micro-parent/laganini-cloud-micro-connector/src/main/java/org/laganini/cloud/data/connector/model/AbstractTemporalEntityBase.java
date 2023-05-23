package org.laganini.cloud.data.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractTemporalEntityBase {

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    protected AbstractTemporalEntityBase() {
        this(null, null);
    }

    protected AbstractTemporalEntityBase(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
