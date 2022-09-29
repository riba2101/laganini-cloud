package com.laganini.cloud.test.testcontainer;

import lombok.NonNull;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

public class MariaDBContainer
        extends org.testcontainers.containers.MariaDBContainer<MariaDBContainer> {

    public static final String IMAGE_VERSION = "mariadb:10.5.3";

    private static MariaDBContainer container;

    private MariaDBContainer(String dockerImageName) {
        super(dockerImageName);
    }

    public static MariaDBContainer getInstance() {
        return MariaDBContainer.getInstance(IMAGE_VERSION);
    }

    public static MariaDBContainer getInstance(String dockerImageName) {
        if (container == null) {
            container = new MariaDBContainer(dockerImageName)
                    .withConfigurationOverride("sql")
                    .waitingFor(Wait.forListeningPort());
        }

        return container;
    }

    public MariaDBContainer whitFixatedPort(int from, int to) {
        addFixedExposedPort(from, to);
        return this;
    }

    @Override
    public MariaDBContainer waitingFor(@NonNull WaitStrategy waitStrategy) {
        setWaitStrategy(waitStrategy);

        return this;
    }

}
