package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional
public abstract class AbstractJpaCrudService<ID, ENTITY extends IdentityEntity<ID>, REPO extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractJpaCruService<ID, ENTITY, REPO>
        implements CrudService<ID, ENTITY>
{

    protected AbstractJpaCrudService(REPO repository) {
        super(repository);
    }

    @Override
    @Transactional
    public Mono<Void> delete(ID id) {
        return find(id)
                .flatMap(ignore -> {
                    try {
                        repository.deleteById(id);
                        return Mono.empty().then();
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(Collection<ID> ids) {
        return findAll(ids)
                .buffer()
                .flatMap(entities -> {
                    try {
                        repository.deleteAll(entities);
                        return Mono.empty().then();
                    } catch (Exception e) {
                        return Mono.error(e);
                    }
                })
                .then();
    }

}
