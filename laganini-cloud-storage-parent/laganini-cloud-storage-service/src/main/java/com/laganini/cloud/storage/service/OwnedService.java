package org.laganini.cloud.storage.service;

import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Flux;

public interface OwnedService<ID, ENTITY extends OwnedEntity<ID>>
        extends CrudService<ID, ENTITY>
{

    Flux<ENTITY> findByOwnerId(Long id);

}
