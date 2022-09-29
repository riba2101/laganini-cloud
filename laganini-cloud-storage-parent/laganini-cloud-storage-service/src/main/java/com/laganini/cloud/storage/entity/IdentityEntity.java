package com.laganini.cloud.storage.entity;

public interface IdentityEntity<ID>
        extends TemporalEntity
{

    ID getId();

    void setId(ID id);

}
