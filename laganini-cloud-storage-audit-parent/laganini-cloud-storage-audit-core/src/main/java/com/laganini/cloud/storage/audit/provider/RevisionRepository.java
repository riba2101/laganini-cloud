package com.laganini.cloud.storage.audit.provider;

import com.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RevisionRepository<T extends RevisionEntity> extends CrudRepository<T, String> {
}
