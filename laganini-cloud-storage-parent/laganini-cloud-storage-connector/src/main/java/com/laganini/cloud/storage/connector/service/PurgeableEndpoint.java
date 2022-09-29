package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.validation.group.Create;
import org.laganini.cloud.validation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface PurgeableEndpoint<ID, IN extends Fetchable<ID>, OUT extends Fetchable<ID>> {

    // Filter mappings
    String ID         = "id";
    String OWNER_ID   = "ownerId";
    String DELETED_AT = "deletedAt";
    String CREATED_AT = "createdAt";
    String UPDATED_AT = "updatedAt";

    @Validated
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    Mono<FilteredAndSorted<OUT>> filter(@RequestBody @Valid Filter filter);

    @Validated
    @RequestMapping(path = "/find", method = RequestMethod.POST)
    Mono<OUT> find(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/find-by-ids", method = RequestMethod.POST)
    Flux<OUT> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/find-by-owner-id", method = RequestMethod.POST)
    Flux<OUT> findByOwnerId(@RequestBody @Valid @NotNull Id<Long> id);

    @Validated(Create.class)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    Mono<OUT> create(@RequestBody @Valid @NotNull IN model);

    @Validated(Update.class)
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    Mono<OUT> update(@RequestBody @Valid @NotNull IN model);

    @Validated
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/delete-by-ids", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/mark-as-deleted", method = RequestMethod.POST)
    Mono<Void> markAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/mark-as-deleted-by-ids", method = RequestMethod.POST)
    Mono<Void> markAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted", method = RequestMethod.POST)
    Mono<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted-by-ids", method = RequestMethod.POST)
    Mono<Void> unmarkAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

    @Validated
    @RequestMapping(path = "/purge", method = RequestMethod.POST)
    Mono<Void> purge(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/purge-by-ids", method = RequestMethod.POST)
    Mono<Void> purge(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

}
