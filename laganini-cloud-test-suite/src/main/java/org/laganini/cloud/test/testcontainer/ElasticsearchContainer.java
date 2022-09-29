package org.laganini.cloud.test.testcontainer;

import lombok.NonNull;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

public class ElasticsearchContainer
        extends org.testcontainers.elasticsearch.ElasticsearchContainer {

    public static final String IMAGE_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:7.14.1";

    private static ElasticsearchContainer container;

    private ElasticsearchContainer(String dockerImageName) {
        super(dockerImageName);
    }

    public static ElasticsearchContainer getInstance() {
        return ElasticsearchContainer.getInstance(IMAGE_VERSION);
    }

    public static ElasticsearchContainer getInstance(String dockerImageName) {
        if (container == null) {
            container = new ElasticsearchContainer(dockerImageName).waitingFor(Wait.forListeningPort());
        }

        return container;
    }

    public ElasticsearchContainer whitFixatedPort(int from, int to) {
        addFixedExposedPort(from, to);
        return this;
    }

    @Override
    public ElasticsearchContainer waitingFor(@NonNull WaitStrategy waitStrategy) {
        setWaitStrategy(waitStrategy);

        return this;
    }

}