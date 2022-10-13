package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.validation.group.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UpdatableEndpoint<ID, IN extends Fetchable<ID>, OUT extends Fetchable<ID>> {

    @Validated(Update.class)
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    Mono<OUT> update(@RequestBody @Valid @NotNull IN model);

}
