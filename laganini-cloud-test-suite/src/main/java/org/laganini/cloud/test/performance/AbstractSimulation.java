package org.laganini.cloud.test.performance;

import io.gatling.javaapi.core.Simulation;

/**
 * Extending class must be a public static class
 */
public abstract class AbstractSimulation extends Simulation {

    public static final String contextField = "context";
    public static SimulationContext context;

}
