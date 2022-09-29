package org.laganini.cloud.storage.audit.provider.r2dbc;

import org.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import org.laganini.cloud.storage.audit.provider.r2dbc.entity.R2dbcRevisionEntry;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface R2dbcReactiveRevisionEntryRepository extends ReactiveCrudRepository<R2dbcRevisionEntry, String>,
                                                       RevisionEntryReactiveRepository<R2dbcRevisionEntry>
{

    @Query("SELECT * FROM revision_entry WHERE revision_id = :revisionId ORDER BY instant DESC LIMIT 1")
    Mono<R2dbcRevisionEntry> findTop1ByRevisionIdOrderByInstantDesc(String revisionId);

    @Query("SELECT * FROM revision_entry WHERE revision_id = :revisionId ORDER BY instant DESC")
    Flux<R2dbcRevisionEntry> findAllByRevisionIdOrderByInstantDesc(String revisionId);

}
