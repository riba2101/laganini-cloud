package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
public interface R2dbcFilterableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends R2dbcRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, FilterableService<ID, ENTITY>
{

    @Override
    default Mono<FilteredAndSorted<ENTITY>> filter(Filter filter) {
        return Mono.just(getRepository().filter(
                filter.getFilters(),
                filter.getSorts(),
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize()
        ));
    }

}
