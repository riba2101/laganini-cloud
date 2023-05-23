package org.laganini.cloud.data.jpa.test;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.laganini.cloud.data.test.MariaDBContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.HashMap;
import java.util.Map;

@Testcontainers
public class MariaDbContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Container
    public static final MariaDBContainer SQL_CONTAINER = new MariaDBContainer();

    @SneakyThrows
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SQL_CONTAINER.start();

        TestPropertyValues values = TestPropertyValues.of(buildProperties());

        values.applyTo(applicationContext);
    }

    @NotNull
    protected Map<String, String> buildProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("spring.datasource.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.datasource.username", SQL_CONTAINER.getUsername());
        props.put("spring.datasource.password", SQL_CONTAINER.getPassword());
        props.put("spring.datasource.hikari.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.datasource.hikari.username", SQL_CONTAINER.getUsername());
        props.put("spring.datasource.hikari.password", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.flyway.user", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.password", SQL_CONTAINER.getUsername());

        return props;
    }


}