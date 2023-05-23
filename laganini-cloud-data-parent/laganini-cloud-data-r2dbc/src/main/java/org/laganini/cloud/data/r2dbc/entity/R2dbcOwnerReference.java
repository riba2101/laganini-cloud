package org.laganini.cloud.data.r2dbc.entity;

import com.querydsl.core.annotations.QueryEmbeddable;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@QueryEmbeddable
public class R2dbcOwnerReference<ID> extends org.laganini.cloud.data.entity.OwnerReference<ID> {

    @Setter(AccessLevel.PROTECTED)
    private ID ownerId;

    public R2dbcOwnerReference(ID ownerId) {
        this.ownerId = ownerId;
    }

}
