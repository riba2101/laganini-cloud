package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import org.springframework.data.repository.CrudRepository;

public interface JpaRevisionEntryRepository
        extends CrudRepository<JpaRevisionEntry, String>, RevisionEntryRepository<JpaRevisionEntry>
{
}
