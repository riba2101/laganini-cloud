package org.laganini.cloud.storage.entity;

import java.time.LocalDateTime;

public interface TemporalEntity {

    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getUpdatedAt();

    void setUpdatedAt(LocalDateTime updatedAt);

}
