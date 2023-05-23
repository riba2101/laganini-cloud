package org.laganini.cloud.data.test;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.RecursiveComparisonAssert;
import org.laganini.cloud.storage.connector.model.Id;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

public interface ControllerTestSuiteTrait<ID, RESPONSE, TARGET> {

    TARGET getTargetApi();

    default RESPONSE givenResponse(ModelBuilder<RESPONSE> builder) {
        return builder.build();
    }

    default Id<ID> givenId(ModelBuilder<Id<ID>> builder) {
        return builder.build();
    }

    default Collection<Id<ID>> givenIds(ModelBuilder<Collection<Id<ID>>> builder) {
        return builder.build();
    }

    default <T> Consumer<T> getVoidMatcher() {
        return (v) -> {};
    }

    default <T> Consumer<T> getDefaultMatcher(T expected, String... ignoredFields) {
        return (v) -> doAssert(expected, v, ignoredFields);
    }

    default <T> RecursiveComparisonAssert<?> doAssert(T expected, T actual, String... ignoredFields) {
        return Assertions
                .assertThat(actual)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .withComparatorForType(Comparator.comparingDouble(Number::doubleValue), Number.class)
                .ignoringFields(ignoredFields)
                .isEqualTo(expected);
    }

    default void test(Publisher<RESPONSE> publisher, Consumer matcher) {
            StepVerifier
                    .create(publisher)
                    .recordWith(ArrayList::new)
                    .thenConsumeWhile(__ -> true)
                    .consumeRecordedWith(value -> {
                        if(Flux.class.isAssignableFrom(publisher.getClass())){
                            matcher.accept(value);
                        }

                        if(Mono.class.isAssignableFrom(publisher.getClass())){
                            matcher.accept(value.stream().findFirst().orElse(null));
                        }
                    })
                    .verifyComplete();
    }

}
