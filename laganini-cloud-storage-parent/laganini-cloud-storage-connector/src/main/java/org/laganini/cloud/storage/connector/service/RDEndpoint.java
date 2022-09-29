package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.connector.model.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface RDEndpoint<ID, OUT extends Fetchable<ID>> {

    // Default filter mappings
    String ID         = "id";
    String CREATED_AT = "createdAt";
    String UPDATED_AT = "updatedAt";

    @Validated
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    Mono<FilteredAndSorted<OUT>> filter(@RequestBody @Valid @NotNull Filter filter);

    @Validated
    @RequestMapping(path = "/find", method = RequestMethod.POST)
    Mono<OUT> find(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/find-by-ids", method = RequestMethod.POST)
    Flux<OUT> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/delete-by-ids", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

}
