package com.laganini.cloud.rmi.service;

import com.laganini.cloud.test.testcontainer.MariaDBContainer;
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
    private static final MariaDBContainer SQL_CONTAINER = MariaDBContainer
            .getInstance()
            .whitFixatedPort(13306, 3306)
            .waitingFor(Wait.forListeningPort());

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SQL_CONTAINER.start();

        Map<String, String> props = new HashMap<>();
        props.put("spring.datasource.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.datasource.password", SQL_CONTAINER.getPassword());
        props.put("spring.datasource.username", SQL_CONTAINER.getUsername());
        props.put("spring.flyway.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.flyway.user", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.password", SQL_CONTAINER.getUsername());

        TestPropertyValues values = TestPropertyValues.of(props);

        values.applyTo(applicationContext);
    }

}