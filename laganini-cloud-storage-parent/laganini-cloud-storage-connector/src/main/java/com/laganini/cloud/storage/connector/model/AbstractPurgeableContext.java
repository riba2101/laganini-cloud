package org.laganini.cloud.storage.connector.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractPurgeableContext<ID>
        extends AbstractOwnedContext<ID>
{

    protected AbstractPurgeableContext(ID id, Long ownerId) {
        super(id, ownerId);
    }

}

