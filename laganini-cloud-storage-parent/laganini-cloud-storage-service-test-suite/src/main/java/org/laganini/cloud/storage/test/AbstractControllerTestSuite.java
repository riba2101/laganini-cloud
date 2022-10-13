package org.laganini.cloud.storage.test;

import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@Getter
public abstract class AbstractControllerTestSuite<ID, RESPONSE>
        implements ControllerTestSuiteTrait<ID, RESPONSE>
{

    private final String             basePath;
    private final ApplicationContext applicationContext;
    private final WebTestClient      webTestClient;

    protected AbstractControllerTestSuite(ApplicationContext applicationContext, String basePath) {
        this.applicationContext = applicationContext;
        this.basePath = basePath;
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext).build();
    }

}
