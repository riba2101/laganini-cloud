package com.laganini.cloud.storage.r2dbc.service;

import com.laganini.cloud.storage.entity.IdentityEntity;
import com.laganini.cloud.storage.repository.FilterRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional
public abstract class AbstractR2DBCCruService<ID, ENTITY extends IdentityEntity<ID>, REPO extends R2dbcRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractR2DBCFilterService<ID, ENTITY, REPO>
{

    protected AbstractR2DBCCruService(REPO repository) {
        super(repository);
    }

    public Mono<ENTITY> persist(ENTITY entity) {
        return repository.save(entity);
    }

    public Flux<ENTITY> persist(Collection<ENTITY> entities) {
        return repository.saveAll(entities);
    }

    public Mono<ENTITY> create(ENTITY entity) {
        return beforeCreate(entity)
                .flatMap(this::persist)
                .flatMap(this::afterCreate);
    }

    @Transactional(readOnly = true)
    public Mono<ENTITY> refresh(ENTITY entity) {
        return find(entity.getId());
    }

    protected Mono<ENTITY> beforeCreate(ENTITY entity) {
        return Mono.just(entity);
    }

    protected Mono<ENTITY> afterCreate(ENTITY entity) {
        return Mono.just(entity);
    }

    public Mono<ENTITY> update(ENTITY entity) {
        return beforeUpdate(entity)
                .flatMap(e -> persist(onUpdate(e, entity)))
                .flatMap(this::afterUpdate);

    }

    protected Mono<ENTITY> beforeUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    protected Mono<ENTITY> afterUpdate(ENTITY entity) {
        return Mono.just(entity);
    }

    protected ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setId(before.getId());
        after.setCreatedAt(before.getCreatedAt());
        after.setUpdatedAt(before.getUpdatedAt());

        return after;
    }

}
