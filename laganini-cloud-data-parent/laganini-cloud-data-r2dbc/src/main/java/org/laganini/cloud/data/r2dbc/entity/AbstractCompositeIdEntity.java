package org.laganini.cloud.data.r2dbc.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laganini.cloud.data.entity.IdentityEntity;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class AbstractCompositeIdEntity<KEY>
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
