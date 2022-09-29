package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

interface ElasticsearchReactiveRevisionEntryRepository
        extends ReactiveElasticsearchRepository<ElasticsearchReactiveRevisionEntry, String>,
                RevisionEntryReactiveRepository<ElasticsearchReactiveRevisionEntry>
{
}
