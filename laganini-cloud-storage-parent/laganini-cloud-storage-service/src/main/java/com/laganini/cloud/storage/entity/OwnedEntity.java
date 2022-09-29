package org.laganini.cloud.storage.entity;

public interface OwnedEntity<ID>
        extends IdentityEntity<ID>
{

    Long getOwnerId();

    void setOwnerId(Long ownerId);

}
