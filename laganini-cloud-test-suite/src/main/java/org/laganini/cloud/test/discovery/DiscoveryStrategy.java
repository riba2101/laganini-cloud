package org.laganini.cloud.test.discovery;

public interface DiscoveryStrategy {

    boolean applies(Class<?> testClazz);

}
