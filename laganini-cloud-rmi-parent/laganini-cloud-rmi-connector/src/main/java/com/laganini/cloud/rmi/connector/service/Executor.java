package com.laganini.cloud.rmi.connector.service;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface Executor<IN, OUT> {

    @Validated
    @RequestMapping(path = "/execute", method = RequestMethod.POST)
    OUT execute(@RequestBody @Valid @NotNull IN model);

}
