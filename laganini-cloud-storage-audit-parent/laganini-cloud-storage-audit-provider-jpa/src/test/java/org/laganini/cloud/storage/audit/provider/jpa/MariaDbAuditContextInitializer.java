package org.laganini.cloud.storage.audit.provider.jpa;

import org.jetbrains.annotations.NotNull;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

@Testcontainers
public class MariaDbAuditContextInitializer extends MariaDbContextInitializer {

    @Override
    protected @NotNull Map<String, String> buildProperties() {
        Map<String, String> props = super.buildProperties();
        props.put("laganini.storage.audit.jpa.datasource.url", SQL_CONTAINER.getJdbcUrl());
        props.put("laganini.storage.audit.jpa.datasource.username", SQL_CONTAINER.getUsername());
        props.put("laganini.storage.audit.jpa.datasource.password", SQL_CONTAINER.getPassword());

        return props;
    }
}
