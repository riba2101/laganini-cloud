package org.laganini.cloud.storage.connector.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEntityBase<ID>
        extends AbstractTemporalEntityBase
        implements Fetchable<ID>
{

    private ID id;

    protected AbstractEntityBase(ID id, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        super(createdAt, modifiedAt);

        this.id = id;
    }

}
