package org.laganini.cloud.storage.endpoint.owned;

import org.laganini.cloud.storage.connector.model.*;
import org.laganini.cloud.storage.connector.service.OwnedEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface FilterableOwnedControllerSupport<
        ID,
        OWNER_ID,
        ENTITY extends IdentityEntity<ID> & OwnedEntity<OWNER_ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends FindableOwnedControllerSupport<ID, OWNER_ID, ENTITY, RESPONSE>

{

    default Mono<Filter> adjustFilter(Owner<OWNER_ID> owner, Filter filter) {
        return getOwnerAdjustedFilter(owner, filter);
    }

    default Mono<Filter> getOwnerAdjustedFilter(Owner<OWNER_ID> owner, Filter filter) {
        if (isSystem(owner) || isAdmin(owner)) {
            return Mono.just(filter);
        }

        Map<String, FilterCriteria<?>> filterCriteriaByField = getByField(filter);

        filterCriteriaByField.put(
                OwnedEndpoint.OWNER_ID,
                new FilterCriteria<>(OwnedEndpoint.OWNER_ID, Operator.EQUALS, owner.getId())
        );

        return Mono.just(new Filter(
                filterCriteriaByField.values(),
                filter.getSorts(),
                filter.getPageable()
        ));
    }

}
