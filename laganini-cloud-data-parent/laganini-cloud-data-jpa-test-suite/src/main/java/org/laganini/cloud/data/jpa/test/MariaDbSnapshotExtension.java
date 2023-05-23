package org.laganini.cloud.data.jpa.test;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.laganini.cloud.data.test.MariaDBContainer;

public class MariaDbSnapshotExtension implements BeforeAllCallback, BeforeEachCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        MariaDBContainer container = MariaDbContextInitializer.SQL_CONTAINER;
        container.restore();
        container.snapshot();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        MariaDBContainer container = MariaDbContextInitializer.SQL_CONTAINER;
        container.restore();
    }

}
