package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.logging.author.AuthorProvider;
import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevisionEntry;
import com.laganini.cloud.storage.audit.service.AbstractRevisionEntryService;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;

@Slf4j
public class ElasticsearchRevisionEntryService extends AbstractRevisionEntryService<ElasticsearchRevisionEntry> {

    public ElasticsearchRevisionEntryService(
            ElasticsearchRevisionEntryRepository repository,
            RevisionEntryConverter<RevisionEntry, ElasticsearchRevisionEntry> converter,
            AuthorProvider authorProvider,
            DiffService diffService,
            Clock clock
    )
    {
        super(repository, converter, authorProvider, diffService, clock);
    }

}
