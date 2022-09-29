package com.laganini.cloud.storage.r2dbc.service;

import com.laganini.cloud.storage.entity.IdentityEntity;
import com.laganini.cloud.storage.repository.FilterRepository;
import com.laganini.cloud.storage.service.CrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional
public abstract class AbstractR2DBCCrudService<ID, ENTITY extends IdentityEntity<ID>, REPO extends R2dbcRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractR2DBCCruService<ID, ENTITY, REPO>
        implements CrudService<ID, ENTITY>
{

    public AbstractR2DBCCrudService(REPO repository) {
        super(repository);
    }

    @Transactional
    public Mono<Void> delete(ID id) {
        return find(id)
                .flatMap(ignore -> repository.deleteById(id));
    }

    @Transactional
    public Mono<Void> delete(Collection<ID> ids) {
        return findAll(ids)
                .collectList()
                .flatMap(repository::deleteAll);
    }

}
