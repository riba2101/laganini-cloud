package org.laganini.cloud.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class JpaTemporalReference extends org.laganini.cloud.data.entity.TemporalReference {

    @Column(nullable = false, updatable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime updatedAt;

    public JpaTemporalReference(LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
