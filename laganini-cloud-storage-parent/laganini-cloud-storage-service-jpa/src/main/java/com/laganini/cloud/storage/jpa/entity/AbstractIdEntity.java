package com.laganini.cloud.storage.jpa.entity;

import com.laganini.cloud.storage.entity.IdentityEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

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

    @Override
    public ID getId() {
        return id;
    }

}
