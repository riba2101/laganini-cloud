package org.laganini.cloud.test.unit;

import org.laganini.cloud.test.discovery.AbstractDiscoveryStrategy;

public class UnitTestDiscoveryStrategy extends AbstractDiscoveryStrategy {

    private static final String SUFFIX_UNIT_TEST   = "Test";
    private static final String PROPERTY_UNIT_TEST = "skip.tests.unit";

    @Override
    protected String getProperty() {
        return PROPERTY_UNIT_TEST;
    }

    @Override
    protected String getSuffix() {
        return SUFFIX_UNIT_TEST;
    }

}
