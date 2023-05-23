package org.laganini.cloud.test.integration;

import org.laganini.cloud.test.discovery.AbstractDiscoveryStrategy;

public class IntegrationTestDiscoveryStrategy extends AbstractDiscoveryStrategy {

    private static final String SUFFIX_INTEGRATION_TEST   = "IT";
    private static final String PROPERTY_INTEGRATION_TEST = "skip.tests.integration";

    @Override
    protected String getProperty() {
        return PROPERTY_INTEGRATION_TEST;
    }

    @Override
    protected String getSuffix() {
        return SUFFIX_INTEGRATION_TEST;
    }

}
