package com.laganini.cloud.storage.suite;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class DatabaseMigrationExtension
        implements BeforeEachCallback
{

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        E2ETest annotation = context.getRequiredTestClass().getAnnotation(E2ETest.class);

        Flyway flyway = SpringExtension.getApplicationContext(context).getBean(Flyway.class);

        if (annotation.clean()) {
            flyway.clean();
        }
        if (annotation.migrate()) {
            flyway.migrate();
        }
    }

}
