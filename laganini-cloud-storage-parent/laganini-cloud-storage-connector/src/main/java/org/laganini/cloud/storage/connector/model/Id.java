package org.laganini.cloud.storage.connector.model;

import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Id<ID>
        extends AbstractEntityBaseContext<ID>
{

    public Id(ID id) {
        super(id);
    }

}
