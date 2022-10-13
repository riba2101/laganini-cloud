package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.provider.RevisionRepository;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRevisionRepository extends JpaRepository<JpaRevision, String>, RevisionRepository<JpaRevision> {
}
