package com.laganini.cloud.storage.audit.service;

import com.laganini.cloud.storage.audit.annotation.Audited;
import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.entity.RevisionEntity;
import com.laganini.cloud.storage.audit.provider.RevisionReactiveRepository;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import com.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Mono;

import static com.laganini.cloud.storage.audit.utils.AuditedUtils.buildKey;

public class AbstractRevisionReactiveService<E extends RevisionEntity> implements RevisionReactiveService {
    protected final RevisionReactiveRepository<E>  repository;
    protected final RevisionConverter<Revision, E> converter;

    public AbstractRevisionReactiveService(
            RevisionReactiveRepository<E> repository,
            RevisionConverter<Revision, E> converter
    )
    {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public Mono<Revision> get(String name, Object id) {
        return get(AuditedUtils.buildKey(name, id));
    }

    @Override
    public Mono<Revision> get(String key) {
        return repository
                .findById(key)
                .map(converter::to);
    }

    @Override
    public Mono<Revision> get(Object entity) {
        if (entity == null) {
            return Mono.empty();
        }

        Audited audited = AuditedUtils.getAudited(entity);
        if (audited == null) {
            return Mono.empty();
        }

        if (!IdentityEntity.class.isAssignableFrom(entity.getClass())) {
            return Mono.empty();
        }

        String key = AuditedUtils.buildKey(
                AuditedUtils.buildName(audited, entity),
                ((IdentityEntity<?>) entity).getId()
        );

        return get(key);
    }

    public Mono<Revision> getOrCreate(String name, String type, Object id) {
        return repository
                .findById(buildKey(name, id))
                .map(converter::to)
                .switchIfEmpty(create(name, type, id));
    }

    public Mono<Revision> create(String name, String type, Object id) {
        return repository
                .save(converter.from(new Revision(
                        buildKey(name, id),
                        type
                )))
                .map(converter::to);
    }
}
