package org.laganini.cloud.data.elasticsearch.test;

import lombok.NonNull;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.time.Duration;

public class ElasticsearchContainer
        extends org.testcontainers.elasticsearch.ElasticsearchContainer
{

    protected static final String PASSWORD_KEY = "ELASTIC_PASSWORD";

    public static final String IMAGE_VERSION = "docker.elastic.co/elasticsearch/elasticsearch:8.5.1";

    public ElasticsearchContainer() {
        this(IMAGE_VERSION);
    }

    public ElasticsearchContainer(String dockerImageName) {
        super(dockerImageName);

        setWaitStrategy(Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(180L)));
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

    public String getUsername() {
        return "elastic";
    }

    public String getPassword() {
        return getEnvMap().get(PASSWORD_KEY);
    }

}