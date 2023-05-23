package org.laganini.cloud.data.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractPurgeableContext<ID>
        extends AbstractEntityBaseContext<ID>
{

    public AbstractPurgeableContext() {
        this(null);
    }

    protected AbstractPurgeableContext(ID id) {
        super(id);
    }

}

