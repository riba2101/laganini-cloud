package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.DeletableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional
public interface R2dbcDeletableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, DeletableService<ID>
{

    @Override
    @Transactional
    default Mono<Void> delete(ID id) {
        return getRepositoryHolder().getRepository().deleteById(id);
    }

    @Override
    @Transactional
    default Flux<Void> delete(Collection<ID> ids) {
        return Flux.from(getRepositoryHolder().getRepository().deleteAllById(ids));
    }

}
