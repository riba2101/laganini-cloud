package com.laganini.cloud.storage.endpoint;

import com.laganini.cloud.storage.ModelBuilder;
import com.laganini.cloud.storage.suite.ReactiveWebIntegrationTestSuite;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.RecursiveComparisonAssert;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

public abstract class ReactiveApiControllerTestSuite<MODEL, RESPONSE>
        extends ReactiveWebIntegrationTestSuite
{

    private final String basePath;

    protected ReactiveApiControllerTestSuite(ApplicationContext context, String basePath) {
        super(context);

        this.basePath = basePath;
    }

    protected MODEL givenModel(ModelBuilder<MODEL> builder) {
        return builder.build();
    }

    protected RESPONSE givenResponse(ModelBuilder<RESPONSE> builder) {
        return builder.build();
    }

    protected <T> void test(
            String path,
            HttpMethod httpMethod,
            Object body,
            Consumer<T> matcher,
            HttpStatus httpStatus,
            Class<T> response
    )
    {
        webTestClient
                .mutate()
                .responseTimeout(Duration.ofSeconds(15L))
                .build()
                .method(httpMethod)
                .uri(basePath + path)
                .accept(MediaType.ALL)
                .headers(getHeaders())
                .bodyValue(body)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBody(response)
                .value(matcher::accept);
    }

    protected <T> void testCollection(
            String path,
            HttpMethod httpMethod,
            Object body,
            Consumer<Collection<T>> matcher,
            HttpStatus httpStatus,
            Class<T> response
    )
    {
        webTestClient
                .mutate()
                .responseTimeout(Duration.ofSeconds(15L))
                .build()
                .method(httpMethod)
                .uri(basePath + path)
                .accept(MediaType.ALL)
                .headers(getHeaders())
                .bodyValue(body)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus)
                .expectBodyList(response)
                .value(matcher::accept);
    }

    protected <T> Consumer<T> getDefaultMatcher(T expected, String[] ignoredFields) {
        return (v) -> doAssert(expected, v, ignoredFields);
    }

    protected <T> RecursiveComparisonAssert<?> doAssert(T expected, T actual, String[] ignoredFields) {
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

    protected String[] getGeneratedFields() {
        return new String[]{"createdAt", "modifiedAt"};
    }

    protected <T> Consumer<T> getVoidMatcher() {
        return (v) -> {};
    }

    protected Consumer<HttpHeaders> getHeaders() {
        return (headers) -> headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }

}
