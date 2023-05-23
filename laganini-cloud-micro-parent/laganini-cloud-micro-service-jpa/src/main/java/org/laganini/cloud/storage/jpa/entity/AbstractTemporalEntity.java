package org.laganini.cloud.storage.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.laganini.cloud.storage.entity.TemporalEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractTemporalEntity
        implements TemporalEntity
{

    @CreatedDate
    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;

}
