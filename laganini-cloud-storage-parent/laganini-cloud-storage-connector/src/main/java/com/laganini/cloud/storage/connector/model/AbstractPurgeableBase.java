package com.laganini.cloud.storage.connector.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPurgeableBase<ID>
        extends AbstractOwnedBase<ID>
{

    private LocalDateTime deletedAt;

    public AbstractPurgeableBase(
            ID id,
            Long ownerId,
            LocalDateTime deletedAt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    )
    {
        super(id, ownerId, createdAt, modifiedAt);

        this.deletedAt = deletedAt;
    }
}

