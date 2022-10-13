package org.laganini.cloud.storage.jpa.test;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.laganini.cloud.storage.test.MariaDBContainer;

public class MariaDbSnapshotExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        MariaDBContainer container = MariaDbContextInitializer.SQL_CONTAINER;
        if (container.snapshots()) {
            container.snapshot();
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        MariaDBContainer container = MariaDbContextInitializer.SQL_CONTAINER;
        if (container.snapshots()) {
            container.restore();
        }
    }

}
