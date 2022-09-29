package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevisionEntry;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

interface ElasticsearchRevisionEntryRepository extends ElasticsearchRepository<ElasticsearchRevisionEntry, String>,
                                                       RevisionEntryRepository<ElasticsearchRevisionEntry>
{
}
