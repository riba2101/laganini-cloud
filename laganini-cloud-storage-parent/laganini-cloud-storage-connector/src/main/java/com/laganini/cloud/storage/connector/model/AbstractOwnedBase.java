package org.laganini.cloud.storage.connector.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractOwnedBase<ID>
        extends AbstractEntityBase<ID>
{

    private Long ownerId;

    public AbstractOwnedBase(
            ID id,
            Long ownerId,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    )
    {
        super(id, createdAt, modifiedAt);

        this.ownerId = ownerId;
    }
}

