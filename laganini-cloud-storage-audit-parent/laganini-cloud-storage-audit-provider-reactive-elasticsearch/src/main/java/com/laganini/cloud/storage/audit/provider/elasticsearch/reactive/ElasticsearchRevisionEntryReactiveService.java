package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import com.laganini.cloud.storage.audit.service.AbstractRevisionEntryReactiveService;

import java.time.Clock;

public class ElasticsearchRevisionEntryReactiveService
        extends AbstractRevisionEntryReactiveService<ElasticsearchReactiveRevisionEntry>
{

    public ElasticsearchRevisionEntryReactiveService(
            ElasticsearchReactiveRevisionEntryRepository repository,
            RevisionEntryConverter<RevisionEntry, ElasticsearchReactiveRevisionEntry> converter,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService,
            Clock clock
    )
    {
        super(repository, converter, authorReactiveProvider, diffService, clock);
    }

}
