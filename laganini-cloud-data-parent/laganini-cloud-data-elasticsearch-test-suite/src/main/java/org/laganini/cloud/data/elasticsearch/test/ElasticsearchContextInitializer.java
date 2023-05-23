package org.laganini.cloud.data.elasticsearch.test;

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

        TestPropertyValues values = TestPropertyValues.of(buildProperties());

        values.applyTo(applicationContext);
    }

    protected Map<String, String> buildProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("spring.elasticsearch.uris", "https://" + ELASTICSEARCH_CONTAINER.getHttpHostAddress());
        props.put("spring.elasticsearch.username", ELASTICSEARCH_CONTAINER.getUsername());
        props.put("spring.elasticsearch.password", ELASTICSEARCH_CONTAINER.getPassword());

        return props;
    }

}