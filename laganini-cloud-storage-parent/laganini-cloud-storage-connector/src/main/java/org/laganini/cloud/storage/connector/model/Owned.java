package org.laganini.cloud.storage.connector.model;

public interface Owned<OWNER_ID> {

    Owner<OWNER_ID> getOwner();

    void setOwner(Owner<OWNER_ID> owner);

}
