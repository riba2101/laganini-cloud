package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.test.testcontainer.ElasticsearchContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class TestcontainersContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    private static final ElasticsearchContainer ELASTICSEARCH_CONTAINER = ElasticsearchContainer
            .getInstance()
            .whitFixatedPort(19200, 9200)
            .whitFixatedPort(19300, 9300)
            .waitingFor(Wait.forHttp("/"));

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ELASTICSEARCH_CONTAINER.start();

        Map<String, String> props = new HashMap<>();
        props.put(
                "spring.data.elasticsearch.client.reactive.endpoints",
                ELASTICSEARCH_CONTAINER.getHost() + ":" + ELASTICSEARCH_CONTAINER.getMappedPort(9200)
        );
        props.put("spring.data.elasticsearch.client.reactive.username", "elastic");
        props.put("spring.data.elasticsearch.client.reactive.password", "changeme");


        TestPropertyValues values = TestPropertyValues.of(props);

        values.applyTo(applicationContext);
    }

}