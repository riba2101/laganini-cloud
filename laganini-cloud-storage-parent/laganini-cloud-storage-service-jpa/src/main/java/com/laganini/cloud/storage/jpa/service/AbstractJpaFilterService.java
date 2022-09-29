package com.laganini.cloud.storage.jpa.service;

import com.laganini.cloud.storage.connector.model.Filter;
import com.laganini.cloud.storage.connector.model.FilteredAndSorted;
import com.laganini.cloud.storage.repository.FilterRepository;
import com.laganini.cloud.storage.service.AbstractService;
import com.laganini.cloud.storage.service.FilterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Slf4j
@Transactional(readOnly = true)
public abstract class AbstractJpaFilterService<ID, ENTITY, REPO extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractService<ENTITY, REPO>
        implements FilterService<ID, ENTITY>
{

    protected AbstractJpaFilterService(REPO repository) {
        super(repository);
    }

    @Override
    public Mono<FilteredAndSorted<ENTITY>> filter(Filter filter) {
        return Mono.just(repository.filter(
                filter.getFilters(),
                filter.getSorts(),
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize()
        ));
    }

    @Override
    public Flux<ENTITY> findAll(Collection<ID> ids) {
        return Flux.fromIterable(repository.findAllById(ids));
    }

    @Override
    public Mono<ENTITY> find(ID id) {
        return Mono.justOrEmpty(repository.findById(id));
    }

}
