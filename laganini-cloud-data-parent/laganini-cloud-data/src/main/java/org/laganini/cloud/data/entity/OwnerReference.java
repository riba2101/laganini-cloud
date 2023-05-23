package org.laganini.cloud.data.entity;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerReference<OWNER_ID> {

    @Setter(AccessLevel.PROTECTED)
    private OWNER_ID id;

    public OwnerReference(OWNER_ID id) {
        this.id = id;
    }

}
