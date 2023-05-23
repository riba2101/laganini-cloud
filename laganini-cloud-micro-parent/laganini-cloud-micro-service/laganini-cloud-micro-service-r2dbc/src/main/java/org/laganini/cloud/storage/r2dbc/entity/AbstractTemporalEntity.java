package org.laganini.cloud.storage.r2dbc.entity;

import com.querydsl.core.annotations.QueryEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.laganini.cloud.storage.entity.TemporalEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@QueryEntity
public abstract class AbstractTemporalEntity
        implements TemporalEntity
{

    @NotNull
    @CreatedDate
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
