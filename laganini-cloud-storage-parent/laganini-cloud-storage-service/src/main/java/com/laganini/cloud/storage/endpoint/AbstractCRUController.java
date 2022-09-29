package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.service.CRUEndpoint;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.service.CruService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public abstract class AbstractCRUController<ID, MODEL extends IdentityEntity<ID>, CONTEXT extends Fetchable<ID>, RESPONSE extends Fetchable<ID>,
        SERVICE extends CruService<ID, MODEL>>
        extends AbstractFilterController<ID, MODEL, RESPONSE, SERVICE>
        implements CRUEndpoint<ID, CONTEXT, RESPONSE>
{

    protected final Converter<CONTEXT, MODEL> inConverter;

    protected AbstractCRUController(
            SERVICE service,
            Converter<CONTEXT, MODEL> inConverter,
            Converter<MODEL, RESPONSE> outConverter
    )
    {
        super(service, outConverter);

        this.inConverter = inConverter;
    }

    @Validated
    @Override
    public Mono<RESPONSE> create(@RequestBody @Valid @NotNull CONTEXT context) {
        return beforeCreate(context)
                .map(this::from)
                .flatMap(service::create)
                .flatMap(this::afterCreate)
                .flatMap(this::toMono);
    }

    @Validated
    @Override
    public Mono<RESPONSE> update(@RequestBody @Valid @NotNull CONTEXT context) {
        return service
                .find(context.getId())
                .flatMap(model -> beforeUpdate(context, model))
                .flatMap(entity -> service.update(onUpdate(entity, from(context))))
                .flatMap(this::afterUpdate)
                .flatMap(this::toMono);
    }

    protected Mono<CONTEXT> beforeCreate(CONTEXT context) {
        return Mono.just(context);
    }

    protected Mono<MODEL> afterCreate(MODEL model) {
        return Mono.just(model);
    }

    protected Mono<MODEL> beforeUpdate(CONTEXT context, MODEL model) {
        return Mono.just(model);
    }

    protected Mono<MODEL> afterUpdate(MODEL model) {
        return Mono.just(model);
    }

    protected MODEL from(CONTEXT context) {
        return inConverter.convert(context);
    }

    protected MODEL onUpdate(MODEL before, MODEL after) {
        after.setId(before.getId());
        after.setCreatedAt(before.getCreatedAt());
        after.setUpdatedAt(before.getUpdatedAt());

        return after;
    }

}
