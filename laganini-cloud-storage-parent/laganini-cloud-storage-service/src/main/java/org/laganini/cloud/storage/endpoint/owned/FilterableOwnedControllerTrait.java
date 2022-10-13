package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.endpoint.FilterableControllerTrait;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface FilterableOwnedControllerTrait<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        RESPONSE extends Fetchable<ID>,
        SERVICE extends FilterableService<ID, ENTITY>,
        SUPPORT extends FilterableOwnedControllerSupport<ID, OWNER_ID, ENTITY, RESPONSE>
        >
        extends FilterableControllerTrait<ID, ENTITY, RESPONSE, SERVICE, SUPPORT>
{

    @Validated
    @Override
    default Mono<FilteredAndSorted<RESPONSE>> filter(@RequestBody @Valid @NotNull Filter filter) {
        return getSupport()
                .getCurrent()
                .flatMap(owner -> getSupport()
                        .adjustFilter(owner, filter)
                        .flatMap(adjustedFilter -> getService()
                                .filter(adjustedFilter)
                                .map(filtered -> getSupport().to(filtered))
                        )
                );
    }


}
