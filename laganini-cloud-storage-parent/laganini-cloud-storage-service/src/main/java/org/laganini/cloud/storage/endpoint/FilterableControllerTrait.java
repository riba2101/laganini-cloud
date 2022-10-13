package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.connector.service.FilterableEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface FilterableControllerTrait<
        ID,
        ENTITY extends IdentityEntity<ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends FilterableService<ID, ENTITY>,
        SUPPORT extends FilterableControllerSupport<ID, ENTITY, RESPONSE>
        >
        extends FilterableEndpoint<ID, RESPONSE>, BaseControllerTrait<ID, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<FilteredAndSorted<RESPONSE>> filter(@RequestBody @Valid @NotNull Filter filter) {
        return getService()
                .filter(filter)
                .map(getSupport()::to);
    }


}
