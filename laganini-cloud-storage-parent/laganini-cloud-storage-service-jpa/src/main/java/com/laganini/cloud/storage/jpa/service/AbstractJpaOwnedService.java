package com.laganini.cloud.storage.jpa.service;

import com.laganini.cloud.storage.connector.model.Filter;
import com.laganini.cloud.storage.connector.model.FilterCriteria;
import com.laganini.cloud.storage.connector.model.Operator;
import com.laganini.cloud.storage.connector.service.OwnedEndpoint;
import com.laganini.cloud.storage.entity.OwnedEntity;
import com.laganini.cloud.storage.repository.FilterRepository;
import com.laganini.cloud.storage.service.OwnedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.Collections;

@Slf4j
@Transactional
public abstract class AbstractJpaOwnedService<ID, ENTITY extends OwnedEntity<ID>, REPO extends JpaRepository<ENTITY, ID> & FilterRepository<ENTITY>>
        extends AbstractJpaCrudService<ID, ENTITY, REPO>
        implements OwnedService<ID, ENTITY>
{

    protected AbstractJpaOwnedService(REPO repository) {
        super(repository);
    }

    @Override
    public Flux<ENTITY> findByOwnerId(Long id) {
        FilterCriteria<?> filterCriteria = new FilterCriteria<>(
                OwnedEndpoint.OWNER_ID,
                Operator.EQUALS,
                id
        );
        Collection<FilterCriteria<?>> filters = Collections.singletonList(filterCriteria);
        Filter                        filter  = new Filter(filters);
        return filter(filter).flatMapMany(filtered -> Flux.fromIterable(filtered.getData()));
    }

    @Override
    protected ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setOwnerId(before.getOwnerId());

        return super.onUpdate(before, after);
    }

}
