package org.laganini.cloud.storage.r2dbc.entity;

import org.laganini.cloud.storage.entity.TemporalEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class AbstractTemporalEntity
        implements TemporalEntity
{

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime updatedAt;

    protected AbstractTemporalEntity() {
    }

    protected AbstractTemporalEntity(@NotNull LocalDateTime createdAt, @NotNull LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
