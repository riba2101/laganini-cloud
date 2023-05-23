package org.laganini.cloud.data.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurgeableReference {

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime deletedAt;

    public PurgeableReference(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
