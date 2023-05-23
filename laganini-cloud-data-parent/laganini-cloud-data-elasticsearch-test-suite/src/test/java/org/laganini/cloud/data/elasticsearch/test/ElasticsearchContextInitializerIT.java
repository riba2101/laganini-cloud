package org.laganini.cloud.data.elasticsearch.test;

import app.TestApp;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.test.integration.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
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
                .sslContext(ElasticsearchContextInitializer.ELASTICSEARCH_CONTAINER.createSslContextFromCa())
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                ElasticsearchContextInitializer.ELASTICSEARCH_CONTAINER.getUsername(),
                                ElasticsearchContextInitializer.ELASTICSEARCH_CONTAINER.getPassword().toCharArray()
                        );
                    }
                })
                .build();

        HttpRequest request = HttpRequest
                .newBuilder(new URI(environment.getProperty("spring.elasticsearch.uris") + "/_cluster/health"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

}