package org.laganini.cloud.storage.r2dbc.service;

import org.laganini.cloud.storage.connector.model.*;
import org.laganini.cloud.storage.connector.service.OwnedEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.service.OwnedService;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Collections;

@Transactional
public interface R2dbcOwnedService<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        REPOSITORY extends R2dbcRepository<ENTITY, ID> & FilterRepository<ENTITY>
        >
        extends R2dbcService<ID, ENTITY, REPOSITORY>, OwnedService<ID, OWNER_ID, ENTITY>
{

    @Override
    default Flux<ENTITY> findByOwner(Owner<OWNER_ID> id) {
        FilterCriteria<?> filterCriteria = new FilterCriteria<>(
                OwnedEndpoint.OWNER_ID,
                Operator.EQUALS,
                id.getId()
        );
        Collection<FilterCriteria<?>> filters = Collections.singletonList(filterCriteria);
        Filter                        filter  = new Filter(filters);

        FilteredAndSorted<ENTITY> filteredAndSorted = getRepository()
                .filter(
                        filter.getFilters(),
                        filter.getSorts(),
                        filter.getPageable().getPage(),
                        filter.getPageable().getPageSize()
                );
        return Flux.fromIterable(filteredAndSorted.getData());
    }

}
