package org.laganini.cloud.data.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TemporalReference {

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;

    public TemporalReference(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
