package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.service.DeletableEndpoint;
import org.laganini.cloud.storage.service.DeletableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface DeletableControllerTrait<ID, SERVICE extends DeletableService<ID>, SUPPORT extends DeletableControllerSupport<ID>>
        extends DeletableEndpoint<ID>, BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id) {
        return getService().delete(id.getId());
    }

    @Validated
    @Override
    default Flux<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return getService().delete(getSupport().flattenIds(ids));
    }

}
