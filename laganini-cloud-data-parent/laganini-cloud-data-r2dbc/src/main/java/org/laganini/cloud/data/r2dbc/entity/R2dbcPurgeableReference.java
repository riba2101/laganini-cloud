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
public class R2dbcPurgeableReference extends org.laganini.cloud.data.entity.PurgeableReference {

    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime deletedAt;

    public R2dbcPurgeableReference(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
