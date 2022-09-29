package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.logging.author.AuthorReactiveProvider;
import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import org.laganini.cloud.storage.audit.service.AbstractRevisionEntryReactiveService;

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
