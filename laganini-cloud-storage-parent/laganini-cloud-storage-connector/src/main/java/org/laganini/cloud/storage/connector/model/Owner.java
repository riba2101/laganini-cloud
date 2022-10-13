package org.laganini.cloud.storage.connector.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class Owner<ID> {

    private final ID id;

    protected Owner() {
        this(null);
    }

    public Owner(ID id) {
        this.id = id;
    }

}
