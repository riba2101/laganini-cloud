package org.laganini.cloud.data.connector.endpoint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.validation.group.Create;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

public interface CreatebleReactiveEndpoint<ID, IN extends Fetchable<ID>, OUT extends Fetchable<ID>> {

    @Validated(Create.class)
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    Mono<OUT> create(@RequestBody @Valid @NotNull IN model);

}
