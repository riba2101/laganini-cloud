package com.laganini.cloud.storage.service;

import com.laganini.cloud.storage.entity.PurgeableEntity;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface PurgeableService<ID, ENTITY extends PurgeableEntity<ID>>
        extends OwnedService<ID, ENTITY>
{

    Mono<Void> markAsDeleted(@RequestBody @Valid @NotNull ID id);

    Mono<Void> markAsDeleted(@RequestBody @Valid @NotNull Collection<ID> ids);

    Mono<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull ID id);

    Mono<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull Collection<ID> ids);

    Mono<Void> purge(ID id);

    Mono<Void> purge(Collection<ID> ids);

}
