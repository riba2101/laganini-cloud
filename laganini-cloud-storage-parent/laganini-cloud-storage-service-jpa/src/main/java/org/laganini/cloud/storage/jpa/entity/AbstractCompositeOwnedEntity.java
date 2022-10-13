package org.laganini.cloud.storage.jpa.entity;

import lombok.*;
import org.laganini.cloud.storage.entity.OwnedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractCompositeOwnedEntity<KEY>
        extends AbstractCompositeIdEntity<KEY>
        implements OwnedEntity<KEY>
{

    @NotNull
    @Column(nullable = false)
    private Long ownerId;

    public AbstractCompositeOwnedEntity(KEY key, Long ownerId) {
        super(key);

        this.ownerId = ownerId;
    }

}
