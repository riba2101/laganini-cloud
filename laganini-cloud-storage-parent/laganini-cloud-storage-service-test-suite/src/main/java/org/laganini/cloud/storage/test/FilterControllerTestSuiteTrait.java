package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

public interface FilterControllerTestSuiteTrait<ID, RESPONSE extends Fetchable<ID>>
        extends ControllerTestSuiteTrait<ID, RESPONSE>
{

    @Test
    default void shouldFilter() {
        testFilter(givenFilter(getFilterBuilder()), HttpStatus.OK, givenFilteredResponse(getFilterResponseBuilder()));
    }

    ModelBuilder<Filter> getFilterBuilder();

    ModelBuilder<FilteredAndSorted<RESPONSE>> getFilterResponseBuilder();

    default FilteredAndSorted<RESPONSE> givenFilteredResponse(ModelBuilder<FilteredAndSorted<RESPONSE>> builder) {
        return builder.build();
    }

    default void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/filter",
                HttpMethod.POST,
                filter,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            FilteredAndSorted<RESPONSE> response
    )
    {
        Class<FilteredAndSorted<RESPONSE>> responseClass = (Class<FilteredAndSorted<RESPONSE>>) response.getClass();
        testFilter(filter, httpStatus, getFilterMatcher(response), responseClass);
    }

    default void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            Consumer<FilteredAndSorted<RESPONSE>> matcher,
            Class<FilteredAndSorted<RESPONSE>> responseClass
    )
    {
        test("/filter", HttpMethod.POST, filter, matcher, httpStatus, responseClass);
    }

    default Consumer<FilteredAndSorted<RESPONSE>> getFilterMatcher(
            FilteredAndSorted<RESPONSE> expected
    )
    {
        return (v) -> {
            doAssert(expected, v, "data");

            doAssert(
                    expected.getData(),
                    v.getData(),
                    getIgnoredFields()
            );
        };
    }

    default Filter givenFilter(ModelBuilder<Filter> builder) {
        return builder.build();
    }

}
