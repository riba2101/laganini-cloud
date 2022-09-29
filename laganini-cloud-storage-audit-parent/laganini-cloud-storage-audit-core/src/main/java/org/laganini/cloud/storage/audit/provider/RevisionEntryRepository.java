package org.laganini.cloud.storage.audit.provider;

import org.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.Optional;

@NoRepositoryBean
public interface RevisionEntryRepository<T extends RevisionEntryEntity> extends CrudRepository<T, String> {

    Optional<T> findTop1ByRevisionIdOrderByInstantDesc(String revisionId);

    Collection<T> findAllByRevisionIdOrderByInstantDesc(String revisionId);

}