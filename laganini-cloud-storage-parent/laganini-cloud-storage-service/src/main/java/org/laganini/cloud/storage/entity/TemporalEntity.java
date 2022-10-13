package org.laganini.cloud.storage.entity;

import java.time.LocalDateTime;

public interface TemporalEntity {

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

}
