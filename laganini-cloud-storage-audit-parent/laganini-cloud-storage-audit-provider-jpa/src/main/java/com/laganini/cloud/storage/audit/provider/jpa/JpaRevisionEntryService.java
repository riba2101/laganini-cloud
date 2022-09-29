package com.laganini.cloud.storage.audit.provider.jpa;

import com.laganini.cloud.logging.author.AuthorProvider;
import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import com.laganini.cloud.storage.audit.service.AbstractRevisionEntryService;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;

@Slf4j
public class JpaRevisionEntryService extends AbstractRevisionEntryService<JpaRevisionEntry> {

    public JpaRevisionEntryService(
            JpaRevisionEntryRepository repository,
            RevisionEntryConverter<RevisionEntry, JpaRevisionEntry> converter,
            AuthorProvider authorProvider,
            DiffService diffService, Clock clock
    )
    {
        super(repository, converter, authorProvider, diffService, clock);
    }

}
