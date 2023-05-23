package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.FindableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional(readOnly = true)
public interface R2dbcFindableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, FindableService<ID, ENTITY>
{

    @Override
    default Flux<ENTITY> findAll(Collection<ID> ids) {
        return getRepositoryHolder().getRepository().findAllById(ids);
    }

    @Override
    default Mono<ENTITY> find(ID id) {
        return getRepositoryHolder().getRepository().findById(id);
    }

}
