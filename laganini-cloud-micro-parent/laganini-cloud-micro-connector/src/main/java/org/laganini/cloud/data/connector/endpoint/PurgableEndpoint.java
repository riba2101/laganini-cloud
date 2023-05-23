package org.laganini.cloud.data.connector.endpoint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.connector.model.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

public interface PurgableEndpoint<ID> {

    // Filter mappings
    String DELETED_AT = "deletedAt";

    @Validated
    @RequestMapping(path = "/mark-as-deleted", method = RequestMethod.POST)
    void markAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/mark-as-deleted-by-ids", method = RequestMethod.POST)
    void markAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted", method = RequestMethod.POST)
    void unmarkAsDeleted(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/unmark-as-deleted-by-ids", method = RequestMethod.POST)
    void unmarkAsDeleted(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

    @Validated
    @RequestMapping(path = "/purge", method = RequestMethod.POST)
    void purge(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/purge-by-ids", method = RequestMethod.POST)
    void purge(@RequestBody @Valid @NotNull Collection<Id<ID>> id);

}
