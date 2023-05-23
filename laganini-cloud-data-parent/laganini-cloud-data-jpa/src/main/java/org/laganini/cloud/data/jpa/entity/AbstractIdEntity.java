package org.laganini.cloud.data.jpa.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.laganini.cloud.data.entity.IdentityEntity;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@MappedSuperclass
public abstract class AbstractIdEntity<ID>
        implements IdentityEntity<ID>
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public ID id;

    protected AbstractIdEntity() {
    }

}
