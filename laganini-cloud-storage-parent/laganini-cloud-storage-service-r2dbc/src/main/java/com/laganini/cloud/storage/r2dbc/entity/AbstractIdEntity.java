package com.laganini.cloud.storage.r2dbc.entity;

import com.laganini.cloud.storage.entity.IdentityEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractIdEntity<ID>
        extends AbstractTemporalEntity
        implements IdentityEntity<ID>
{

    @Id
    public ID id;

    protected AbstractIdEntity() {
    }

    protected AbstractIdEntity(ID id) {
        this.id = id;
    }

}
