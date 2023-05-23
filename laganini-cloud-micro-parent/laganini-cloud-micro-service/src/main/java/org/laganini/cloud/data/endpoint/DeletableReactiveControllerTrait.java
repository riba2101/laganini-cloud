package org.laganini.cloud.data.endpoint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.connector.endpoint.DeletableReactiveEndpoint;
import org.laganini.cloud.data.connector.model.Id;
import org.laganini.cloud.data.service.DeletableReactiveService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface DeletableReactiveControllerTrait<ID, SERVICE extends DeletableReactiveService<ID>, SUPPORT extends DeletableControllerSupport<ID>>
        extends DeletableReactiveEndpoint<ID>, BaseControllerTrait<ID, SERVICE, SUPPORT>
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
