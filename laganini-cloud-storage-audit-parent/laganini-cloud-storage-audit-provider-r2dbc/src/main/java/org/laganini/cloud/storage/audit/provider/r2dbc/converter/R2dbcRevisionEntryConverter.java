package org.laganini.cloud.storage.audit.provider.r2dbc.converter;

import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevisionEntry;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;

public class R2dbcRevisionEntryConverter implements RevisionEntryConverter<RevisionEntry, R2dbcRevisionEntry> {


    @Override
    public RevisionEntry to(R2dbcRevisionEntry target) {
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
    public R2dbcRevisionEntry from(RevisionEntry source) {
        long version = source.getVersion();

        return new R2dbcRevisionEntry(
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
