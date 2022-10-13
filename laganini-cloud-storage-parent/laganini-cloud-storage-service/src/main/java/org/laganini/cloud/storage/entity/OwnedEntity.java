package org.laganini.cloud.storage.entity;

public interface OwnedEntity<OWNER_ID> {

    OwnerReference<OWNER_ID> getOwner();

}
