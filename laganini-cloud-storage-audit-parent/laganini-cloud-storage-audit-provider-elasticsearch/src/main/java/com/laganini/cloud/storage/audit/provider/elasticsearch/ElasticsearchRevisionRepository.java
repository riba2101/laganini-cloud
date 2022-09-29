package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.storage.audit.provider.RevisionRepository;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

interface ElasticsearchRevisionRepository
        extends ElasticsearchRepository<ElasticsearchRevision, String>, RevisionRepository<ElasticsearchRevision>
{
}
