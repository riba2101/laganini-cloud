package org.laganini.cloud.data.test;

import lombok.Getter;
import org.laganini.cloud.rmi.test.FeignClientBuilder;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Getter
public abstract class AbstractControllerTestSuite<ID, RESPONSE, TARGET>
        implements ControllerTestSuiteTrait<ID, RESPONSE, TARGET>,
                   ApplicationContextAware
{

    @LocalServerPort
    private int                serverPort;
    private TARGET             targetApi;
    private ApplicationContext applicationContext;

    protected abstract Class<TARGET> getTargetApiClass();

    protected String getBaseUrl() {
        return "http://localhost:" + serverPort;
    }

    public TARGET getTargetApi() {
        if (targetApi == null) {
            targetApi = FeignClientBuilder.build(applicationContext, getTargetApiClass(), getBaseUrl());
        }

        return targetApi;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
