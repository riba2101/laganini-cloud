package org.laganini.cloud.storage.elasticsearch.test;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class ElasticsearchContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static final ElasticsearchContainer ELASTICSEARCH_CONTAINER = new ElasticsearchContainer();

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ELASTICSEARCH_CONTAINER.start();

        Map<String, String> props = new HashMap<>();
        props.put("spring.elasticsearch.uris", ELASTICSEARCH_CONTAINER.getHttpHostAddress());

        TestPropertyValues values = TestPropertyValues.of(props);

        values.applyTo(applicationContext);
    }

}