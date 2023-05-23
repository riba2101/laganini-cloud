package org.laganini.cloud.storage.jpa.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public abstract class AbstractIdEntity<ID>
        extends AbstractTemporalEntity
        implements IdentityEntity<ID>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public ID id;

    protected AbstractIdEntity() {
    }

}
