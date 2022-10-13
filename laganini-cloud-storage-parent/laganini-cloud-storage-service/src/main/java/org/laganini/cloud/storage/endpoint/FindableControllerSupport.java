package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.core.convert.converter.Converter;

public interface FindableControllerSupport<
        ID,
        ENTITY extends IdentityEntity<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends DeletableControllerSupport<ID>
{

    Converter<ENTITY, RESPONSE> getOutConverter();

    default RESPONSE to(ENTITY model) {
        return getOutConverter().convert(model);
    }

}
