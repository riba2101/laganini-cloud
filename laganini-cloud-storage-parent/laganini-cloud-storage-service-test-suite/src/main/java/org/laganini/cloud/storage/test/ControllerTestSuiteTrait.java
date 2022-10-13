package org.laganini.cloud.storage.test;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.RecursiveComparisonAssert;
import org.laganini.cloud.storage.connector.model.Id;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

public interface ControllerTestSuiteTrait<ID, RESPONSE> {

    String getBasePath();

    WebTestClient getWebTestClient();

    default RESPONSE givenResponse(ModelBuilder<RESPONSE> builder) {
        return builder.build();
    }

    default Id<ID> givenId(ModelBuilder<Id<ID>> builder) {
        return builder.build();
    }

    default Collection<Id<ID>> givenIds(ModelBuilder<Collection<Id<ID>>> builder) {
        return builder.build();
    }

    default  <T> Consumer<T> getVoidMatcher() {
        return (v) -> {};
    }

    default <T> void test(
            String path,
            HttpMethod httpMethod,
            Object body,
            Consumer<T> matcher,
            HttpStatus httpStatus,
            Class<T> response
    )
    {
        getWebTestClient()
                .mutate()
                .responseTimeout(Duration.ofSeconds(15L))
                .build()
                .method(httpMethod)
                .uri(getBasePath() + path)
                .accept(MediaType.ALL)
                .headers(getHeaders())
                .bodyValue(body)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBody(response)
                .value(matcher::accept);
    }

    default <T> void testCollection(
            String path,
            HttpMethod httpMethod,
            Object body,
            Consumer<Collection<T>> matcher,
            HttpStatus httpStatus,
            Class<T> response
    )
    {
        getWebTestClient()
                .mutate()
                .responseTimeout(Duration.ofSeconds(15L))
                .build()
                .method(httpMethod)
                .uri(getBasePath() + path)
                .accept(MediaType.ALL)
                .headers(getHeaders())
                .bodyValue(body)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBodyList(response)
                .value(matcher::accept);
    }

    default <T> Consumer<T> getDefaultMatcher(T expected, String... ignoredFields) {
        return (v) -> doAssert(expected, v, ignoredFields);
    }

    default <T> RecursiveComparisonAssert<?> doAssert(T expected, T actual, String... ignoredFields) {
        int epsilon = 1; //1 sec

        return Assertions
                .assertThat(actual)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .withComparatorForType(Comparator.comparingInt(Number::intValue), Number.class)
                .withComparatorForType((o1, o2) -> {
                    if (o1.isSupported(ChronoUnit.SECONDS)) {
                        if (Math.abs(o1.until(o2, ChronoUnit.SECONDS)) > epsilon) {
                            return o1.until(o2, ChronoUnit.SECONDS) < 0 ? -1 : 1;
                        }
                    }
                    if (o1.isSupported(ChronoUnit.DAYS)) {
                        if (Math.abs(o1.until(o2, ChronoUnit.DAYS)) > epsilon) {
                            return o1.until(o2, ChronoUnit.DAYS) < 0 ? -1 : 1;
                        }
                    }

                    return 0;
                }, Temporal.class)
                .ignoringFields(ignoredFields)
                .isEqualTo(expected);
    }

    default String[] getIgnoredFields() {
        return new String[]{"createdAt", "modifiedAt"};
    }

    default Consumer<HttpHeaders> getHeaders() {
        return (headers) -> headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
