package org.laganini.cloud.test.performance;

import org.laganini.cloud.test.discovery.AbstractDiscoveryStrategy;

public class PerformanceTestDiscoveryStrategy extends AbstractDiscoveryStrategy {

    private static final String SUFFIX_PERFORMANCE_TEST   = "PT";
    private static final String PROPERTY_PERFORMANCE_TEST = "skip.tests.performance";

    @Override
    protected String getProperty() {
        return PROPERTY_PERFORMANCE_TEST;
    }

    @Override
    protected String getSuffix() {
        return SUFFIX_PERFORMANCE_TEST;
    }

}
