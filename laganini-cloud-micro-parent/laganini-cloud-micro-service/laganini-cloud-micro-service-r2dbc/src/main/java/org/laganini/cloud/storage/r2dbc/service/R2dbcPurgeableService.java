package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.PurgeableEntity;
import org.laganini.cloud.storage.service.PurgeableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public interface R2dbcPurgeableService<
        ID,
        ENTITY extends PurgeableEntity<ID>,
        REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>
        >
        extends R2dbcService<ID, ENTITY, REPOSITORY>,
                R2dbcFindableService<ID, ENTITY, REPOSITORY>,
                R2dbcPeristableService<ID, ENTITY, REPOSITORY>,
                R2dbcDeletableService<ID, ENTITY, REPOSITORY>,
                PurgeableService<ID>
{

    @Override
    default Mono<Void> markAsDeleted(ID id) {
        return find(id)
                .flatMap(entity -> {
                    entity.setDeletedAt(LocalDateTime.now());
                    return persist(entity);
                })
                .then();
    }

    @Override
    default Flux<Void> markAsDeleted(Collection<ID> ids) {
        LocalDateTime now = LocalDateTime.now();
        return findAll(ids)
                .map(entity -> {
                    entity.setDeletedAt(now);
                    return entity;
                })
                .collectList()
                .flatMapMany(this::persist)
                .thenMany(Mono.empty().then());
    }

    @Override
    default Mono<Void> unmarkAsDeleted(ID id) {
        return find(id)
                .flatMap(entity -> {
                    entity.setDeletedAt(null);
                    return persist(entity);
                })
                .then();
    }

    @Override
    default Flux<Void> unmarkAsDeleted(Collection<ID> ids) {
        return findAll(ids)
                .map(entity -> {
                    entity.setDeletedAt(null);
                    return entity;
                })
                .collectList()
                .flatMapMany(this::persist)
                .thenMany(Mono.empty().then());
    }

    @Override
    default Mono<Void> purge(ID id) {
        return find(id)
                .flatMap(entity -> delete(id));
    }

    @Override
    default Flux<Void> purge(Collection<ID> ids) {
        return findAll(ids)
                .buffer()
                .flatMap(entities -> {
                             Set<ID> targets = entities
                                     .stream()
                                     .map(IdentityEntity::getId)
                                     .collect(Collectors.toSet());
                             return delete(targets);
                         }
                )
                .thenMany(Mono.empty().then());
    }

    @Override
    default ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setDeletedAt(before.getDeletedAt());

        return R2dbcPeristableService.super.onUpdate(before, after);
    }

}
