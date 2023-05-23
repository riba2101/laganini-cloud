package org.laganini.cloud.data.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class JpaOwnerReference<ID> extends org.laganini.cloud.data.entity.OwnerReference<ID> {

    @Column
    @Setter(AccessLevel.PROTECTED)
    private ID ownerId;

    public JpaOwnerReference(ID ownerId) {
        this.ownerId = ownerId;
    }

}
