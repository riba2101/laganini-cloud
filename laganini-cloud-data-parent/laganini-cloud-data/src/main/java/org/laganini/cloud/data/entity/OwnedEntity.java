package org.laganini.cloud.data.entity;

public interface OwnedEntity<OWNER_ID, T extends OwnerReference<OWNER_ID>> {

    T getOwner();

    void setOwner(T ownerReference);

}
