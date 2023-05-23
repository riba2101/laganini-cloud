package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.laganini.cloud.storage.service.DeletableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Transactional
public interface JpaDeletableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, DeletableService<ID>
{

    @Override
    @Transactional
    default Mono<Void> delete(ID id) {
        try {
            getRepositoryHolder().getRepository().deleteById(id);
            return Mono.empty().then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    @Transactional
    default Flux<Void> delete(Collection<ID> ids) {
        try {
            getRepositoryHolder().getRepository().deleteAllById(ids);
            return Flux.empty().thenMany(Mono.empty().then());
        } catch (Exception e) {
            return Flux.error(e);
        }
    }

}
