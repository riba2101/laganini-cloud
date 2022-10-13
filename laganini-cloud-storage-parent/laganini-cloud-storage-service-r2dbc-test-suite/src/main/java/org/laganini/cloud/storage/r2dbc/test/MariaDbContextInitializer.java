package org.laganini.cloud.storage.r2dbc.test;

import lombok.SneakyThrows;
import org.laganini.cloud.storage.test.MariaDBContainer;
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

        Map<String, String> props = new HashMap<>();
        props.put("spring.r2dbc.url", SQL_CONTAINER.getJdbcUrl().replace("jdbc", "r2dbc"));
        props.put("spring.r2dbc.username", SQL_CONTAINER.getUsername());
        props.put("spring.r2dbc.password", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.url", SQL_CONTAINER.getJdbcUrl());
        props.put("spring.flyway.user", SQL_CONTAINER.getPassword());
        props.put("spring.flyway.password", SQL_CONTAINER.getUsername());

        TestPropertyValues values = TestPropertyValues.of(props);

        values.applyTo(applicationContext);
    }


}