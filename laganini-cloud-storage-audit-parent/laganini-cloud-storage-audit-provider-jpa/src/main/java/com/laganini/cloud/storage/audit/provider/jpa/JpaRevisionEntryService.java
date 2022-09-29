package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.logging.author.AuthorProvider;
import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import org.laganini.cloud.storage.audit.service.AbstractRevisionEntryService;
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
