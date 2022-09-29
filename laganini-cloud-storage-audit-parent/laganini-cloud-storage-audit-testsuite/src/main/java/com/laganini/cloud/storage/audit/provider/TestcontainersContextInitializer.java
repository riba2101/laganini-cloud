package org.laganini.cloud.storage.audit.provider;

import org.laganini.cloud.test.testcontainer.ElasticsearchContainer;
import org.laganini.cloud.test.testcontainer.MariaDBContainer;
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

    @Container
    private static final MariaDBContainer SQL_CONTAINER = MariaDBContainer
            .getInstance()
            .whitFixatedPort(13306, 3306)
            .waitingFor(Wait.forListeningPort());

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ELASTICSEARCH_CONTAINER.start();
        SQL_CONTAINER.start();

        Map<String, String> props = new HashMap<>();
        props.put("spring.datasource.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.datasource.password", SQL_CONTAINER.getPassword());
        props.put("spring.datasource.username", SQL_CONTAINER.getUsername());
        props.put("spring.flyway.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.flyway.user", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.password", SQL_CONTAINER.getUsername());

        props.put(
                "spring.elasticsearch.rest.uris",
                ELASTICSEARCH_CONTAINER.getHost() + ":" + ELASTICSEARCH_CONTAINER.getMappedPort(9200)
        );
//        props.put("spring.data.elast0icsearch.client.reactive.username", "elastic");
//        props.put("spring.data.elasticsearch.client.reactive.password", "changeme");

        TestPropertyValues values = TestPropertyValues.of(props);

        values.applyTo(applicationContext);
    }

}