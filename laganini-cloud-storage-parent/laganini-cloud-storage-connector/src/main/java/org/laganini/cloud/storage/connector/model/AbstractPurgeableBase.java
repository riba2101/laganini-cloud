package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractPurgeableBase<ID>
        extends AbstractEntityBase<ID>
{

    private final LocalDateTime deletedAt;

    protected AbstractPurgeableBase() {
        this(null, null, null, null);
    }

    protected AbstractPurgeableBase(
            ID id,
            LocalDateTime deletedAt,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    )
    {
        super(id, createdAt, modifiedAt);

        this.deletedAt = deletedAt;
    }
}

