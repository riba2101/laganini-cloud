package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.FilterableEndpoint;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;

import java.util.function.Consumer;

public interface FilterControllerTestSuiteTrait<
        ID,
        RESPONSE extends Fetchable<ID>,
        TARGET extends FilterableEndpoint<ID, RESPONSE>
        >
        extends ControllerTestSuiteTrait<ID, FilteredAndSorted<RESPONSE>, TARGET>
{

    @Test
    default void shouldFilter() {
        testFilter(givenFilter(getFilterBuilder()), givenFilteredResponse(getFilterResponseBuilder()));
    }

    ModelBuilder<Filter> getFilterBuilder();

    ModelBuilder<FilteredAndSorted<RESPONSE>> getFilterResponseBuilder();

    default FilteredAndSorted<RESPONSE> givenFilteredResponse(ModelBuilder<FilteredAndSorted<RESPONSE>> builder) {
        return builder.build();
    }

    default void testFilter(
            Filter filter,
            BusinessExceptionResponse exception
    )
    {
        testFilter(filter, getDefaultMatcher(exception));
    }

    default void testFilter(
            Filter filter,
            FilteredAndSorted<RESPONSE> response
    )
    {
        testFilter(filter, getDefaultMatcher(response));
    }

    default void testFilter(
            Filter filter,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().filter(filter), matcher);
    }

    default Filter givenFilter(ModelBuilder<Filter> builder) {
        return builder.build();
    }

}
