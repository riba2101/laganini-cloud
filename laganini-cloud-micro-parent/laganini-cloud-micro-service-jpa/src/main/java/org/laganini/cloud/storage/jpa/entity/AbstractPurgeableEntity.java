package org.laganini.cloud.storage.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import org.laganini.cloud.storage.entity.PurgeableEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractPurgeableEntity<ID>
        extends AbstractIdEntity<ID>
        implements PurgeableEntity<ID>
{

    @Column
    private LocalDateTime deletedAt;

}
