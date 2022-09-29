package org.laganini.cloud.storage.suite;

import org.springframework.context.ApplicationContext;
import org.springframework.test.web.reactive.server.WebTestClient;

public abstract class ReactiveWebIntegrationTestSuite {

    protected final ApplicationContext context;
    protected final WebTestClient      webTestClient;

    protected ReactiveWebIntegrationTestSuite(ApplicationContext context) {
        this.context = context;
        this.webTestClient = WebTestClient.bindToApplicationContext(context).build();
    }

}
