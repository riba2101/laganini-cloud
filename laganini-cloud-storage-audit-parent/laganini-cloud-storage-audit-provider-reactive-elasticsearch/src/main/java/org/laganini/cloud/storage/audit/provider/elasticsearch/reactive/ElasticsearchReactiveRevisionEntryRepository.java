package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

interface ElasticsearchReactiveRevisionEntryRepository
        extends ReactiveElasticsearchRepository<ElasticsearchReactiveRevisionEntry, String>,
                RevisionEntryReactiveRepository<ElasticsearchReactiveRevisionEntry>
{
}
