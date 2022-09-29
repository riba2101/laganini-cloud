package org.laganini.cloud.storage.audit.service;

import org.laganini.cloud.storage.audit.annotation.Audited;
import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.audit.provider.RevisionRepository;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.entity.IdentityEntity;

import java.util.Optional;

public class AbstractRevisionService<E extends RevisionEntity> implements RevisionService {

    protected final RevisionRepository<E>          repository;
    protected final RevisionConverter<Revision, E> converter;

    public AbstractRevisionService(
            RevisionRepository<E> repository,
            RevisionConverter<Revision, E> converter
    )
    {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Optional<Revision> get(String name, Object id) {
        return get(AuditedUtils.buildKey(name, id));
    }

    @Override
    public Optional<Revision> get(String key) {
        return repository
                .findById(key)
                .map(converter::to);
    }

    @Override
    public Optional<Revision> get(Object entity) {
        if (entity == null) {
            return Optional.empty();
        }

        Audited audited = AuditedUtils.getAudited(entity);
        if (audited == null) {
            return Optional.empty();
        }

        if (!IdentityEntity.class.isAssignableFrom(entity.getClass())) {
            return Optional.empty();
        }

        String key = AuditedUtils.buildKey(
                AuditedUtils.buildName(audited, entity),
                ((IdentityEntity<?>) entity).getId()
        );
        return get(key);
    }

    @Override
    public Revision getOrCreate(String name, String type, Object id) {
        return repository
                .findById(AuditedUtils.buildKey(name, id))
                .map(converter::to)
                .orElseGet(() -> create(name, type, id));
    }

    @Override
    public Revision create(String name, String type, Object id) {
        return converter.to(repository.save(converter.from(new Revision(
                AuditedUtils.buildKey(name, id),
                type
        ))));
    }
}
