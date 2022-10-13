package org.laganini.cloud.storage.jpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.laganini.cloud.storage.entity.TemporalEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTemporalEntity
        implements TemporalEntity
{

    @NotNull
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @NotNull
    @LastModifiedDate
    @Column(nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;

}
