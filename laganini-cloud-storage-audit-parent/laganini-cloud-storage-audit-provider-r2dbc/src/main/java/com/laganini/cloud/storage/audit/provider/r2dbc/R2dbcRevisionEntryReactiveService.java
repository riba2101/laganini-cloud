package com.laganini.cloud.storage.audit.provider.r2dbc;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevisionEntry;
import com.laganini.cloud.storage.audit.service.AbstractRevisionEntryReactiveService;
import lombok.extern.slf4j.Slf4j;

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
