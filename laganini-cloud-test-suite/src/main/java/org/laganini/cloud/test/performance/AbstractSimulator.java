package org.laganini.cloud.test.performance;

import lombok.Getter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

/**
 * Extending class must be a non-static class and annotated with @Nested and @EsbPerformanceTest
 */
@Getter
public abstract class AbstractSimulator {

    private final Class<? extends AbstractSimulation> target;
    private final String targetUrl;

    protected AbstractSimulator(Class<? extends AbstractSimulation> target, String targetUrl) {
        this.target = target;
        this.targetUrl = targetUrl;
    }

    public void prepare() {
        buildContext(targetUrl);
    }

    @SneakyThrows
    protected void buildContext(String targetUrl) {
        Field contextField = target.getField(AbstractSimulation.contextField);
        contextField.set(null, new SimulationContext(targetUrl, prepareHeaders(), prepareBody()));
    }

    protected Map<String, String> prepareHeaders() {
        return Collections.emptyMap();
    }

    protected Object prepareBody() {
        return null;
    }

    /**
     * Used to trigger the test AND set static context variables in AbstractSimulation
     */
    @Test
    protected abstract void simulate(GatlingReportParser.Results results);

}
