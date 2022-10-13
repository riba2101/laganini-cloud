package org.laganini.cloud.storage.elasticsearch.test;

import lombok.NonNull;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

public class ElasticsearchContainer
        extends org.testcontainers.elasticsearch.ElasticsearchContainer
{

    public static final String IMAGE_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:7.14.1";

    public ElasticsearchContainer() {
        this(IMAGE_VERSION);
    }

    public ElasticsearchContainer(String dockerImageName) {
        super(dockerImageName);

        waitingFor(Wait.forHttp("/"));
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