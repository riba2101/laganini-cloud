package org.laganini.cloud.storage.audit.provider.r2dbc.converter;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevision;

public class R2dbcRevisionConverter implements RevisionConverter<Revision, R2dbcRevision> {

    @Override
    public Revision to(R2dbcRevision target) {
        return new Revision(
                target.getId(),
                target.getType()
        );
    }

    @Override
    public R2dbcRevision from(Revision source) {
        return new R2dbcRevision(
                source.getKey(),
                source.getType()
        );
    }

}
