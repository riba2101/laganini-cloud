package org.laganini.cloud.data.test;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FlywayExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        Flyway flyway = SpringExtension.getApplicationContext(context).getBean(Flyway.class);

        flyway.migrate();
    }

}
