package org.laganini.cloud.data.audit.service;

import org.laganini.cloud.data.audit.dto.Revision;
import org.laganini.cloud.data.audit.dto.RevisionEntry;
import org.laganini.cloud.data.audit.dto.RevisionOperation;
import org.laganini.cloud.data.audit.entity.RevisionEntity;
import org.laganini.cloud.data.audit.entity.RevisionEntryEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RevisionEntryReactiveService<E extends RevisionEntryEntity> {

    Mono<RevisionEntry> getLatest(Revision revision);

    Flux<RevisionEntry> getAll(Revision revision);

    Mono<E> prepare(RevisionEntity revision, RevisionOperation operation, Object idOrEntity);

}
