package org.laganini.cloud.storage.audit.provider.r2dbc;

import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

public abstract class AbstractPersistable<ID> implements Persistable<ID> {

    @Transient
    private boolean insert = true;

    @Override
    public boolean isNew() {
        return insert;
    }

    public void markInsert() {
        insert = true;
    }

    public void markUpdate() {
        insert = false;
    }

}
