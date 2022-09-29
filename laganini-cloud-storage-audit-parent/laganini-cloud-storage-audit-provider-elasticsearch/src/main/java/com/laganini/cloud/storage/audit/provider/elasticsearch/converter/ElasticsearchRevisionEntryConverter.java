package com.laganini.cloud.storage.audit.provider.elasticsearch.converter;

import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevisionEntry;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;

public class ElasticsearchRevisionEntryConverter
        implements RevisionEntryConverter<RevisionEntry, ElasticsearchRevisionEntry>
{

    @Override
    public RevisionEntry to(ElasticsearchRevisionEntry target) {
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
    public ElasticsearchRevisionEntry from(RevisionEntry source) {
        long version = source.getVersion();

        return new ElasticsearchRevisionEntry(
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
