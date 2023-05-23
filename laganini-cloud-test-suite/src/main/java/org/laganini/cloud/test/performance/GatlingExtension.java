package org.laganini.cloud.test.performance;

import io.gatling.app.Gatling;
import io.gatling.core.config.GatlingPropertiesBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class GatlingExtension implements InvocationInterceptor, ParameterResolver {

    public static final String GATLING_RESULT_ROOT_PATH = "target/gatling/";
    public static final String GATLING_RESULT_FILE = "simulation.log";

    private AbstractSimulator simulator;
    private String uuid;

    @Override
    public <T> T interceptTestClassConstructor(
            Invocation<T> invocation,
            ReflectiveInvocationContext<Constructor<T>> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        T instance = invocation.proceed();
        if (!AbstractSimulator.class.isAssignableFrom(instance.getClass())) {
            throw new IllegalAccessException("EsbPerformanceTest must sit on a AbstractSimulator class");
        }

        uuid = UUID.randomUUID().toString();
        simulator = (AbstractSimulator) instance;

        return instance;
    }

    @Override
    public void interceptTestMethod(
            Invocation<Void> invocation,
            ReflectiveInvocationContext<Method> invocationContext,
            ExtensionContext extensionContext
    ) throws Throwable {
        Class<? extends AbstractSimulation> target = simulator.getTarget();

        GatlingPropertiesBuilder properties = new GatlingPropertiesBuilder();
        properties.simulationClass(target.getCanonicalName());
        properties.runDescription(uuid);
        properties.resultsDirectory(GATLING_RESULT_ROOT_PATH);

        simulator.prepare();
        Gatling.fromMap(properties.build());

        Path simulationPath = findSimulation(uuid);
        GatlingReportParser.Results results = (GatlingReportParser.Results) invocationContext.getArguments().get(0);
        results
                .getResults()
                .addAll(GatlingReportParser
                        .process(simulationPath, GatlingReportParser.GatlingSimulationFormat.v3)
                        .getResults());

        invocation.proceed();
    }

    @Override
    public boolean supportsParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(GatlingReportParser.Results.class);
    }

    @SneakyThrows
    @Override
    public Object resolveParameter(
            ParameterContext parameterContext,
            ExtensionContext extensionContext
    ) throws ParameterResolutionException {
        return new GatlingReportParser.Results(new ArrayList<>());
    }

    @SneakyThrows
    private Path findSimulation(String uuid) {
        Path root = Path.of(GATLING_RESULT_ROOT_PATH);
        List<Path> simulations = Files
                .walk(root, 2)
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().equals(GATLING_RESULT_FILE))
                .collect(Collectors.toList());

        return simulations
                .stream()
                .filter(target -> getFirstLine(target)
                        .stream()
                        .anyMatch(line -> line.contains(uuid))
                )
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not load simulation for: " + uuid));
    }

    @SneakyThrows
    private Optional<String> getFirstLine(Path simulation) {
        return Files.lines(simulation).findFirst();
    }

}
