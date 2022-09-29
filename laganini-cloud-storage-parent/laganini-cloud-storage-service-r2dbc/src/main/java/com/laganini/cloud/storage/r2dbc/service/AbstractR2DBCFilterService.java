package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional(readOnly = true)
public abstract class AbstractR2DBCFilterService<ID, ENTITY, REPO extends R2dbcRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractService<ENTITY, REPO>
{

    protected AbstractR2DBCFilterService(REPO repository) {
        super(repository);
    }

    public Mono<FilteredAndSorted<ENTITY>> filter(Filter filter) {
        return Mono.just(repository.filter(
                filter.getFilters(),
                filter.getSorts(),
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize()
        ));
    }

    public Flux<ENTITY> findAll(Collection<ID> ids) {
        return repository.findAllById(ids);
    }

    public Mono<ENTITY> find(ID id) {
        return repository.findById(id);
    }

}
