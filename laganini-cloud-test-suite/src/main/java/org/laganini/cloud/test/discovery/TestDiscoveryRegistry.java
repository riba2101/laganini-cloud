package org.laganini.cloud.test.discovery;

import org.laganini.cloud.test.integration.IntegrationTestDiscoveryStrategy;
import org.laganini.cloud.test.performance.PerformanceTestDiscoveryStrategy;
import org.laganini.cloud.test.unit.UnitTestDiscoveryStrategy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDiscoveryRegistry {

    private static final Map<Class<?>, DiscoveryStrategy> STRATEGIES_BY_CLASS = new ConcurrentHashMap<>();

    static {
        register(new UnitTestDiscoveryStrategy());
        register(new IntegrationTestDiscoveryStrategy());
        register(new PerformanceTestDiscoveryStrategy());
    }

    public static Collection<DiscoveryStrategy> getAll() {
        return STRATEGIES_BY_CLASS.values();
    }

    public static void register(DiscoveryStrategy strategy) {
        STRATEGIES_BY_CLASS.put(strategy.getClass(), strategy);
    }

    public static DiscoveryStrategy remove(DiscoveryStrategy strategy) {
        return STRATEGIES_BY_CLASS.remove(strategy.getClass());
    }

}
