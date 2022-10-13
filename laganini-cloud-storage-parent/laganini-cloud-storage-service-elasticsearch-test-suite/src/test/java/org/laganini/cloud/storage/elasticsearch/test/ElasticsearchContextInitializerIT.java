package org.laganini.cloud.storage.elasticsearch.test;

import app.TestApp;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@IntegrationTest(classes = TestApp.class, initializers = ElasticsearchContextInitializer.class)
class ElasticsearchContextInitializerIT {

    private final Environment environment;

    @Autowired
    ElasticsearchContextInitializerIT(Environment environment) {
        this.environment = environment;
    }

    @Test
    void shouldStartAndSetProperties() {
        Assertions.assertThat(environment.containsProperty("spring.elasticsearch.uris")).isTrue();
    }

    @SneakyThrows
    @Test
    void shouldBeAccessible() {
        HttpClient client = HttpClient
                .newBuilder()
                .build();

        HttpRequest request = HttpRequest
                .newBuilder(new URI("http://" + environment.getProperty("spring.elasticsearch.uris") + "/_cluster/health"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

}