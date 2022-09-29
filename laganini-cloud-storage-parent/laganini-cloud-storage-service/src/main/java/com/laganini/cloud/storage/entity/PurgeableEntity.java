package com.laganini.cloud.storage.entity;

import java.time.LocalDateTime;

public interface PurgeableEntity<ID>
        extends OwnedEntity<ID>
{

    LocalDateTime getDeletedAt();

    void setDeletedAt(LocalDateTime deletedAt);
}
