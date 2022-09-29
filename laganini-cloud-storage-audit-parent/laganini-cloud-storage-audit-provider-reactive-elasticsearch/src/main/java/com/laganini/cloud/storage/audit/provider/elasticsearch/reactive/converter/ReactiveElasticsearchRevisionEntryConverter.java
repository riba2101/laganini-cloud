package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter;

import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;

public class ReactiveElasticsearchRevisionEntryConverter
        implements RevisionEntryConverter<RevisionEntry, ElasticsearchReactiveRevisionEntry>
{

    @Override
    public RevisionEntry to(ElasticsearchReactiveRevisionEntry target) {
        return new RevisionEntry(
                target.getRevisionId(),
                target.getVersion(),
                target.getOperation(),
                target.getAuthor(),
                target.getPrevious(),
                target.getAfter(),
                target.getDiff(),
                target.getInstant()
        );
    }

    @Override
    public ElasticsearchReactiveRevisionEntry from(RevisionEntry source) {
        long version = source.getVersion();

        return new ElasticsearchReactiveRevisionEntry(
                AuditedUtils.buildKey(source.getRevisionId(), version),
                version,
                source.getRevisionId(),
                source.getOperation(),
                source.getAuthor(),
                source.getPrevious(),
                source.getAfter(),
                source.getDiff(),
                source.getInstant()
        );
    }
}
