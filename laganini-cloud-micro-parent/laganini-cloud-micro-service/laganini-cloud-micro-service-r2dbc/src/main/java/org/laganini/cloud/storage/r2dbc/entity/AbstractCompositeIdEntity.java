package org.laganini.cloud.storage.r2dbc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractCompositeIdEntity<KEY>
        extends AbstractTemporalEntity
        implements IdentityEntity<KEY>
{

    @Id
    private KEY key;

    protected AbstractCompositeIdEntity(KEY key) {
        this.key = key;
    }

    @Override
    public KEY getId() {
        return key;
    }

    @Override
    public void setId(KEY key) {
        this.key = key;
    }

}
