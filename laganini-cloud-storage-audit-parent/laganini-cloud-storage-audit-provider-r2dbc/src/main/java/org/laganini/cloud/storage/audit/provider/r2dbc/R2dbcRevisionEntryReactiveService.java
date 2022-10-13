package org.laganini.cloud.storage.audit.provider.r2dbc;

import lombok.extern.slf4j.Slf4j;
import org.laganini.cloud.logging.author.AuthorReactiveProvider;
import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevisionEntry;
import org.laganini.cloud.storage.audit.service.AbstractRevisionEntryReactiveService;

import java.time.Clock;

@Slf4j
public class R2dbcRevisionEntryReactiveService extends AbstractRevisionEntryReactiveService<R2dbcRevisionEntry> {

    public R2dbcRevisionEntryReactiveService(
            R2dbcReactiveRevisionEntryRepository repository,
            RevisionEntryConverter<RevisionEntry, R2dbcRevisionEntry> converter,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService, Clock clock
    )
    {
        super(repository, converter, authorReactiveProvider, diffService, clock);
    }

}
