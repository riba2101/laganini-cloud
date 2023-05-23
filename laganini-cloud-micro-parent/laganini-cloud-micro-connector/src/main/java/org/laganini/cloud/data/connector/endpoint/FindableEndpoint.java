package org.laganini.cloud.data.connector.endpoint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laganini.cloud.data.connector.model.Fetchable;
import org.laganini.cloud.data.connector.model.Id;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

public interface FindableEndpoint<ID, OUT extends Fetchable<ID>> {

    @Validated
    @RequestMapping(path = "/find", method = RequestMethod.POST)
    OUT find(@RequestBody @Valid @NotNull Id<ID> id);

    @Validated
    @RequestMapping(path = "/find-by-ids", method = RequestMethod.POST)
    Collection<OUT> findAll(@RequestBody @Valid @NotNull Collection<Id<ID>> ids);

}
