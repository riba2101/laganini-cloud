package org.laganini.cloud.data.audit.service;

import org.laganini.cloud.data.audit.dto.Revision;
import org.laganini.cloud.data.audit.entity.RevisionEntity;
import org.laganini.cloud.data.entity.IdentityEntity;

import java.util.Optional;

public interface RevisionService<E extends RevisionEntity> {

    Optional<Revision> get(String name, Object id);

    Optional<Revision> get(String key);

    Optional<Revision> get(IdentityEntity<?> entity);

    E getOrPrepare(IdentityEntity<?> entity);

}
