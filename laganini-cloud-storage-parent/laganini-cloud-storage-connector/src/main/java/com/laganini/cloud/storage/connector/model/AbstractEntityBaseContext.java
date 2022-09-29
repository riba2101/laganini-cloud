package com.laganini.cloud.storage.connector.model;

import com.laganini.cloud.validation.group.Create;
import com.laganini.cloud.validation.group.Default;
import com.laganini.cloud.validation.group.Update;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEntityBaseContext<ID>
        implements Fetchable<ID>
{

    @Null(groups = {Create.class})
    @NotNull(groups = {Default.class, Update.class})
    private ID id;

    protected AbstractEntityBaseContext(ID id) {
        this.id = id;
    }

}
