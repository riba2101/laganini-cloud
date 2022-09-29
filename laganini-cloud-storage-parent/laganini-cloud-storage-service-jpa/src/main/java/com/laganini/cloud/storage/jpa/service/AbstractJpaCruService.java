package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.CruService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional
public abstract class AbstractJpaCruService<ID, ENTITY extends IdentityEntity<ID>, REPO extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractJpaFilterService<ID, ENTITY, REPO>
        implements CruService<ID, ENTITY>
{

    protected AbstractJpaCruService(REPO repository) {
        super(repository);
    }

    @Override
    public Mono<ENTITY> persist(ENTITY entity) {
        try {
            return Mono.just(repository.save(entity));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public Flux<ENTITY> persist(Collection<ENTITY> entities) {
        try {
            return Flux.fromIterable(repository.saveAll(entities));
        } catch (Exception e) {
            return Flux.error(e);
        }
    }

    @Override
    public Mono<ENTITY> create(ENTITY entity) {
        return beforeCreate(entity)
                .flatMap(this::persist)
                .flatMap(this::afterCreate);
    }

    @Override
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

    @Override
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
