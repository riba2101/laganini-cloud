package com.laganini.cloud.storage.audit.service;

import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RevisionEntryReactiveService {

    Mono<RevisionEntry> getLatest(Revision revision);

    Flux<RevisionEntry> getAll(Revision revision);

    Mono<RevisionEntry> create(Revision revision, RevisionOperation operation, Object idOrEntity);

}
