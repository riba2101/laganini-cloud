package com.laganini.cloud.storage.jpa.entity;

import com.laganini.cloud.storage.entity.OwnedEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractOwnedEntity<ID>
        extends AbstractIdEntity<ID>
        implements OwnedEntity<ID>
{

    @NotNull
    @Column(nullable = false)
    private Long ownerId;

    public AbstractOwnedEntity(Long ownerId) {
        this.ownerId = ownerId;
    }

}
