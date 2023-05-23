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
public class JpaPurgeableReference extends org.laganini.cloud.data.entity.PurgeableReference {

    @Column
    @Setter(AccessLevel.PROTECTED)
    private LocalDateTime deletedAt;

    public JpaPurgeableReference(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}
