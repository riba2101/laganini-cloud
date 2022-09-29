package com.laganini.cloud.storage.audit.provider.jpa;

import com.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import com.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import org.springframework.data.repository.CrudRepository;

interface JpaRevisionEntryRepository
        extends CrudRepository<JpaRevisionEntry, String>, RevisionEntryRepository<JpaRevisionEntry>
{
}
