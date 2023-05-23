package org.laganini.cloud.data.endpoint.owned;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.endpoint.FilterableControllerTrait;
import org.laganini.cloud.data.service.FilterableService;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Filter;
import org.laganini.cloud.data.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface FilterableOwnedReactiveControllerTrait<
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
