package org.laganini.cloud.storage.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerReference<ID> {

    private ID id;

    public OwnerReference(ID id) {
        this.id = id;
    }

}
