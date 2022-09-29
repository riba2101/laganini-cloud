package org.laganini.cloud.storage.audit.provider.r2dbc;

import org.laganini.cloud.storage.audit.provider.RevisionReactiveRepository;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevision;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface R2dbcReactiveRevisionRepository extends ReactiveCrudRepository<R2dbcRevision, String>,
                                                  RevisionReactiveRepository<R2dbcRevision>
{
}
