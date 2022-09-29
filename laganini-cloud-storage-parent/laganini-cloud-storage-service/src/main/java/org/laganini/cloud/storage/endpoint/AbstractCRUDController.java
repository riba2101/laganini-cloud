package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.connector.service.CRUDEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.CrudService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public abstract class AbstractCRUDController<ID, MODEL extends IdentityEntity<ID>, REQUEST extends Fetchable<ID>, RESPONSE extends Fetchable<ID>,
        SERVICE extends CrudService<ID, MODEL>>
        extends AbstractCRUController<ID, MODEL, REQUEST, RESPONSE, SERVICE>
        implements CRUDEndpoint<ID, REQUEST, RESPONSE>
{

    public AbstractCRUDController(
            SERVICE service,
            Converter<REQUEST, MODEL> inConverter,
            Converter<MODEL, RESPONSE> outConverter
    )
    {
        super(service, inConverter, outConverter);
    }

    @Validated
    @Override
    public Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id) {
        return service.delete(id.getId());
    }

    @Validated
    @Override
    public Mono<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids) {
        return service.delete(flattenIds(ids));
    }

}
