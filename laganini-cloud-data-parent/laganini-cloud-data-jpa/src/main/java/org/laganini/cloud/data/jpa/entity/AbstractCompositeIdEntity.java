package org.laganini.cloud.data.jpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.laganini.cloud.data.entity.IdentityEntity;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractCompositeIdEntity<KEY>
        implements IdentityEntity<KEY>
{

    @EmbeddedId
    private KEY key;

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
