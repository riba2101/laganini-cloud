package org.laganini.cloud.storage.audit.provider;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface RevisionEntryReactiveRepository<T> extends ReactiveCrudRepository<T, String> {

    Mono<T> findTop1ByRevisionIdOrderByInstantDesc(String revisionId);

    Flux<T> findAllByRevisionIdOrderByInstantDesc(String revisionId);

}
