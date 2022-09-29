package com.laganini.cloud.storage.audit.provider.jpa;

import com.laganini.cloud.storage.audit.provider.RevisionRepository;
import com.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.springframework.data.repository.CrudRepository;

interface JpaRevisionRepository extends CrudRepository<JpaRevision, String>, RevisionRepository<JpaRevision> {
}
