package org.laganini.cloud.data.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractEntityBase<ID>
        extends AbstractTemporalEntityBase
        implements Fetchable<ID>
{

    private final ID id;

    protected AbstractEntityBase() {
        this(null, null, null);
    }

    protected AbstractEntityBase(ID id, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(createdAt, modifiedAt);

        this.id = id;
    }

}
