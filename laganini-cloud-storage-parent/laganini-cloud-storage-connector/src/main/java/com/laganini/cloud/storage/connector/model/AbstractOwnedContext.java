package com.laganini.cloud.storage.connector.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractOwnedContext<ID>
        extends AbstractEntityBaseContext<ID>
{

    private Long ownerId;

    protected AbstractOwnedContext(ID id, Long ownerId) {
        super(id);

        this.ownerId = ownerId;
    }

}

