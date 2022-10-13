package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.laganini.cloud.validation.group.Create;
import org.laganini.cloud.validation.group.Default;
import org.laganini.cloud.validation.group.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@ToString
@EqualsAndHashCode
public abstract class AbstractEntityBaseContext<ID>
        implements Fetchable<ID>
{

    @Null(groups = {Create.class})
    @NotNull(groups = {Default.class, Update.class})
    private final ID id;

    protected AbstractEntityBaseContext() {
        this(null);
    }

    protected AbstractEntityBaseContext(ID id) {
        this.id = id;
    }

}
