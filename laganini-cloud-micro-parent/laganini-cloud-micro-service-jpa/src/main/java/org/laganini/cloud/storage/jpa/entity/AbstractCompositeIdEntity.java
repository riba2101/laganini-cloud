package org.laganini.cloud.storage.jpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laganini.cloud.storage.entity.IdentityEntity;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class AbstractCompositeIdEntity<KEY>
        extends AbstractTemporalEntity
        implements IdentityEntity<KEY>
{

    @EmbeddedId
    private KEY key;

    protected AbstractCompositeIdEntity() {
    }

    public AbstractCompositeIdEntity(KEY key) {
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
