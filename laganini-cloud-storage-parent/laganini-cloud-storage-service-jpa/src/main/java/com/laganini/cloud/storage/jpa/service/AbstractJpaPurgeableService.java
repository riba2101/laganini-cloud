package com.laganini.cloud.storage.jpa.service;

import com.laganini.cloud.storage.entity.IdentityEntity;
import com.laganini.cloud.storage.entity.PurgeableEntity;
import com.laganini.cloud.storage.repository.FilterRepository;
import com.laganini.cloud.storage.service.PurgeableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public abstract class AbstractJpaPurgeableService<ID, ENTITY extends PurgeableEntity<ID>,
        REPO extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractJpaOwnedService<ID, ENTITY, REPO>
        implements PurgeableService<ID, ENTITY>
{

    protected AbstractJpaPurgeableService(REPO repository) {
        super(repository);
    }

    @Override
    public Mono<Void> markAsDeleted(@Valid @NotNull ID id) {
        return find(id)
                .flatMap(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    return update(entity);
                })
                .then();
    }

    @Override
    public Mono<Void> markAsDeleted(@Valid @NotNull Collection<ID> ids) {
        return findAll(ids)
                .buffer()
                .flatMap(entities -> {
                    LocalDateTime now = LocalDateTime.now();
                    entities.forEach(entity -> {
                        entity.setDeletedAt(now);
                        repository.save(entity);
                    });

                    return Mono.empty().then();
                })
                .then();
    }

    @Override
    public Mono<Void> unmarkAsDeleted(@Valid @NotNull ID id) {
        return find(id)
                .flatMap(entity -> {
                    entity.setDeletedAt(null);
                    return update(entity);
                })
                .then();
    }

    @Override
    public Mono<Void> unmarkAsDeleted(@Valid @NotNull Collection<ID> ids) {
        return findAll(ids)
                .buffer()
                .flatMap(entities -> {
                    entities.forEach(entity -> {
                        entity.setDeletedAt(null);
                        repository.save(entity);
                    });

                    return Mono.empty().then();
                })
                .then();
    }

    @Override
    public Mono<Void> purge(ID id) {
        return find(id).flatMap(entity -> delete(entity.getId()));
    }

    @Override
    public Mono<Void> purge(Collection<ID> ids) {
        return findAll(ids)
                .buffer()
                .flatMap(entities -> delete(entities
                                                    .stream()
                                                    .map(IdentityEntity::getId)
                                                    .collect(Collectors.toSet()))
                )
                .then();
    }

    @Override
    protected ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setDeletedAt(before.getDeletedAt());

        return super.onUpdate(before, after);
    }

}
