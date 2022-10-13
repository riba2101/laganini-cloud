package org.laganini.cloud.storage.connector.service;

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

public interface PurgableEndpoint<ID> {

    // Filter mappings
    String DELETED_AT = "deletedAt";

    @Validated
    @RequestMapping(path = "/mark-as-deleted", method = RequestMethod.POST)
    Mono<Void> markAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/mark-as-deleted-by-ids", method = RequestMethod.POST)
    Flux<Void> markAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted", method = RequestMethod.POST)
    Mono<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted-by-ids", method = RequestMethod.POST)
    Flux<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

    @Validated
    @RequestMapping(path = "/purge", method = RequestMethod.POST)
    Mono<Void> purge(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/purge-by-ids", method = RequestMethod.POST)
    Flux<Void> purge(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

}
