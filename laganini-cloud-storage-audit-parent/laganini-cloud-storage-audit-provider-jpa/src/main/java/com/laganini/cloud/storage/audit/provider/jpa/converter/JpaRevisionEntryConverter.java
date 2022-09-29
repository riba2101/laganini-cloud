package org.laganini.cloud.storage.audit.provider.jpa.converter;

import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;

public class JpaRevisionEntryConverter implements RevisionEntryConverter<RevisionEntry, JpaRevisionEntry> {

    @Override
    public RevisionEntry to(JpaRevisionEntry target) {
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
    public JpaRevisionEntry from(RevisionEntry source) {
        long version = source.getVersion();

        return new JpaRevisionEntry(
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
