package org.laganini.cloud.data.r2dbc.entity;

import lombok.*;
import org.laganini.cloud.data.entity.IdentityEntity;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractIdEntity<ID>
        implements IdentityEntity<ID>
{

    @Id
    public ID id;

    protected AbstractIdEntity(ID id) {
        this.id = id;
    }

}
