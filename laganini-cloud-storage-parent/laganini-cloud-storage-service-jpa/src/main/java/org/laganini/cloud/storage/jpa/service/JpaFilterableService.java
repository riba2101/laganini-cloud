package org.laganini.cloud.storage.jpa.service;

import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Transactional(readOnly = true)
public interface JpaFilterableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends JpaService<ID, ENTITY, REPOSITORY>, FilterableService<ID, ENTITY>
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
