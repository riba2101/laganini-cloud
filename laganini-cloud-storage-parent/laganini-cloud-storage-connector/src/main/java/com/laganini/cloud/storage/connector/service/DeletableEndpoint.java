package org.laganini.cloud.storage.connector.service;

import org.laganini.cloud.storage.connector.model.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface DeletableEndpoint<ID> {

    @Validated
    @RequestMapping(path = "/delete", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/delete-by-ids", method = RequestMethod.POST)
    Mono<Void> delete(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

}
