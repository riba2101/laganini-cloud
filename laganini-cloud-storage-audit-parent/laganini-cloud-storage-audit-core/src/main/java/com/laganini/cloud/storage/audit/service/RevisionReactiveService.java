package com.laganini.cloud.storage.audit.service;

import com.laganini.cloud.storage.audit.dto.Revision;
import reactor.core.publisher.Mono;

public interface RevisionReactiveService {

    Mono<Revision> get(String name, Object id);

    Mono<Revision> get(String key);

    Mono<Revision> get(Object entity);

    Mono<Revision> getOrCreate(String name, String type, Object id);

    Mono<Revision> create(String name, String type, Object id);

}
