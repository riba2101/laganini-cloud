package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.core.convert.converter.Converter;
import reactor.core.publisher.Mono;

public interface PersistableControllerSupport<
        ID,
        ENTITY extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends FindableControllerSupport<ID, ENTITY, RESPONSE>
{

    Converter<CONTEXT, ENTITY> getInConverter();

    default Mono<CONTEXT> beforeCreate(CONTEXT context) {
        return Mono.just(context);
    }

    default Mono<ENTITY> afterCreate(ENTITY model) {
        return Mono.just(model);
    }

    default Mono<ENTITY> beforeUpdate(CONTEXT context, ENTITY model) {
        return Mono.just(model);
    }

    default Mono<ENTITY> afterUpdate(ENTITY model) {
        return Mono.just(model);
    }

    default ENTITY from(CONTEXT context) {
        return getInConverter().convert(context);
    }

    default ENTITY onUpdate(ENTITY before, ENTITY after) {
        after.setId(before.getId());

        return after;
    }

}
