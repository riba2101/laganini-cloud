package com.laganini.cloud.storage.jpa.entity;

import com.laganini.cloud.storage.entity.PurgeableEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractPurgeableEntity<ID>
        extends AbstractOwnedEntity<ID>
        implements PurgeableEntity<ID>
{

    @Column
    private LocalDateTime deletedAt;

    public AbstractPurgeableEntity(Long ownerId) {
        super(ownerId);
    }

}
