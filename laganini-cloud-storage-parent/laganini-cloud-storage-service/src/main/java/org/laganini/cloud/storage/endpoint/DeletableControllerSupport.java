package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.AbstractEntityBaseContext;
import org.laganini.cloud.storage.connector.model.Id;

import java.util.Collection;
import java.util.stream.Collectors;

public interface DeletableControllerSupport<ID>
        extends ControllerSupport<ID>
{

    default Collection<ID> flattenIds(Collection<Id<ID>> ids) {
        return ids
                .stream()
                .map(AbstractEntityBaseContext::getId)
                .collect(Collectors.toList());
    }

}
