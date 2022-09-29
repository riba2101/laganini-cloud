package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.provider.RevisionRepository;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.springframework.data.repository.CrudRepository;

interface JpaRevisionRepository extends CrudRepository<JpaRevision, String>, RevisionRepository<JpaRevision> {
}
