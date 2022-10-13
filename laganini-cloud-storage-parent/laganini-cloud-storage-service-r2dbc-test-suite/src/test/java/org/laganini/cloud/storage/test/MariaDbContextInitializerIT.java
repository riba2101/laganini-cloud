package org.laganini.cloud.storage.test;

import app.TestApp;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.r2dbc.test.MariaDbContextInitializer;
import org.laganini.cloud.storage.r2dbc.test.MariaDbSnapshotExtension;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.mariadb.r2dbc.MariadbConnectionFactoryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.FetchSpec;
import org.testcontainers.containers.MariaDBR2DBCDatabaseContainer;

import java.util.Map;

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
        Assertions.assertThat(environment.containsProperty("spring.r2dbc.url")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.r2dbc.username")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.r2dbc.password")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.url")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.user")).isTrue();
        Assertions.assertThat(environment.containsProperty("spring.flyway.password")).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldBeAccessible() {
        ConnectionFactoryOptions options = MariaDBR2DBCDatabaseContainer.getOptions(
                MariaDbContextInitializer.SQL_CONTAINER
        );
        ConnectionFactory connectionFactory = new MariadbConnectionFactoryProvider().create(options);
        DatabaseClient    client            = DatabaseClient.create(connectionFactory);

        Map<String, Object> result = client.sql("SELECT 1").fetch().first().block();
        Assertions.assertThat(result).isNotNull();
    }
}