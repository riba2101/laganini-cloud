package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.storage.audit.provider.RevisionReactiveRepository;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

interface ElasticsearchReactiveRevisionRepository
        extends ReactiveElasticsearchRepository<ElasticsearchReactiveRevision, String>,
                RevisionReactiveRepository<ElasticsearchReactiveRevision>
{
}
