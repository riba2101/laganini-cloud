package com.laganini.cloud.storage.service;

import com.laganini.cloud.storage.entity.IdentityEntity;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface CrudService<ID, ENTITY extends IdentityEntity<ID>>
        extends CruService<ID, ENTITY>
{

    Mono<Void> delete(ID id);

    Mono<Void> delete(Collection<ID> ids);

}
