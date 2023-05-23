package org.laganini.cloud.data.r2dbc.entity;

import com.querydsl.core.annotations.QueryEmbeddable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@QueryEmbeddable
public class R2dbcTemporalReference extends org.laganini.cloud.data.entity.TemporalReference {

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;

    public R2dbcTemporalReference(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
