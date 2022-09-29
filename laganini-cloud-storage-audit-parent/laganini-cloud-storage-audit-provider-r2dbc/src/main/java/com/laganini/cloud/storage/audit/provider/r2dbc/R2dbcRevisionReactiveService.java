package org.laganini.cloud.storage.audit.provider.r2dbc;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevision;
import org.laganini.cloud.storage.audit.service.AbstractRevisionReactiveService;

public class R2dbcRevisionReactiveService extends AbstractRevisionReactiveService<R2dbcRevision> {

    public R2dbcRevisionReactiveService(
            R2dbcReactiveRevisionRepository repository,
            RevisionConverter<Revision, R2dbcRevision> converter
    )
    {
        super(repository, converter);
    }

}
