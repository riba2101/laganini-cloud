package org.laganini.cloud.storage.audit.provider;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface RevisionReactiveRepository<T> extends ReactiveCrudRepository<T, String> {
}
