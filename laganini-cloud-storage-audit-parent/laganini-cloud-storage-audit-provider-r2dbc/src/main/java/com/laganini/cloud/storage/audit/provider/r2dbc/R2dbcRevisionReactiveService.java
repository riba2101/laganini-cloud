package com.laganini.cloud.storage.audit.provider.r2dbc;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevision;
import com.laganini.cloud.storage.audit.service.AbstractRevisionReactiveService;

public class R2dbcRevisionReactiveService extends AbstractRevisionReactiveService<R2dbcRevision> {

    public R2dbcRevisionReactiveService(
            R2dbcReactiveRevisionRepository repository,
            RevisionConverter<Revision, R2dbcRevision> converter
    )
    {
        super(repository, converter);
    }

}
