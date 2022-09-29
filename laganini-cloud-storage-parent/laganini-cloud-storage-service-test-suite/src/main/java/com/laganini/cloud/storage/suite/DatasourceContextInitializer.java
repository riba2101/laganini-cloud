package com.laganini.cloud.storage.suite;

import com.laganini.cloud.test.testcontainer.MariaDBContainer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
public class DatasourceContextInitializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext>
{

    @Container
    protected static final MariaDBContainer SQL_CONTAINER = MariaDBContainer.getInstance();

    private static Map<String, Object> createConnectionConfiguration() {
        return Map.of(
                "spring.datasource.url", SQL_CONTAINER.getJdbcUrl(),
                "spring.datasource.username", SQL_CONTAINER.getUsername(),
                "spring.datasource.password", SQL_CONTAINER.getPassword(),
                "spring.flyway.url", SQL_CONTAINER.getJdbcUrl(),
                "spring.flyway.username", SQL_CONTAINER.getUsername(),
                "spring.flyway.password", SQL_CONTAINER.getPassword()
        );
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SQL_CONTAINER.start();

        ConfigurableEnvironment environment = applicationContext.getEnvironment();

        MapPropertySource testcontainers = new MapPropertySource(
                "testcontainers",
                createConnectionConfiguration()
        );

        environment.getPropertySources().addFirst(testcontainers);
    }

}
