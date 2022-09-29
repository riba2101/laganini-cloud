package com.laganini.cloud.storage.connector.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractTemporalEntityBase {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    protected AbstractTemporalEntityBase(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
