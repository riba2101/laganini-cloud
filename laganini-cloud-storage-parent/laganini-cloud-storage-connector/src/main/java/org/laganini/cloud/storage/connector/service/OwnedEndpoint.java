package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Owner;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface OwnedEndpoint<ID, OWNER_ID, OUT extends Fetchable<ID>> {

    // Filter mappings
    String OWNER_ID = "ownerId";

    @Validated
    @RequestMapping(path = "/find-by-owner-id", method = RequestMethod.POST)
    Flux<OUT> findByOwner(@RequestBody @Valid @NotNull Owner<OWNER_ID> id);

}
