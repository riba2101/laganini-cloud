package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.laganini.cloud.storage.service.FindableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
@Transactional(readOnly = true)
public interface JpaFindableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, FindableService<ID, ENTITY>
{

    @Override
    default Flux<ENTITY> findAll(Collection<ID> ids) {
        try {
            return Flux.fromIterable(getRepositoryHolder().getRepository().findAllById(ids));
        } catch (Exception e) {
            return Flux.error(e);
        }
    }

    @Override
    default Mono<ENTITY> find(ID id) {
        try {
            return Mono.justOrEmpty(getRepositoryHolder().getRepository().findById(id));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

}
