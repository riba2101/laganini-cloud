package org.laganini.cloud.storage.test;

import app.TestApp;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.storage.jpa.test.MariaDbSnapshotExtension;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.sql.Connection;
import java.sql.DriverManager;

@ExtendWith({FlywayExtension.class, MariaDbSnapshotExtension.class})
@IntegrationTest(classes = TestApp.class, initializers = MariaDbContextInitializer.class)
class MariaDbContextInitializerIT {

    private final Environment environment;

    @Autowired
    MariaDbContextInitializerIT(Environment environment) {
        this.environment = environment;
    }

    @Test
    void shouldStartAndSetProperties() {
        Assertions.assertThat(environment.containsProperty("spring.datasource.url")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.datasource.username")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.datasource.password")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.url")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.user")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.password")).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldBeAccessible() {
        Connection connection = DriverManager.getConnection(
                environment.getProperty("spring.datasource.url"),
                environment.getProperty("spring.datasource.username"),
                environment.getProperty("spring.datasource.password")
        );

        Assertions.assertThat(connection).isNotNull();

        connection.close();
    }
}