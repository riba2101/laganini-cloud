package org.laganini.cloud.storage.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.laganini.cloud.storage.entity.OwnedEntity;

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
