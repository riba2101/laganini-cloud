package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

public interface UpdateableControllerTestSuiteTrait<ID, CONTEXT extends Fetchable<ID>, RESPONSE extends Fetchable<ID>>
        extends ControllerTestSuiteTrait<ID, RESPONSE>
{

    @Test
    default void shouldUpdate() {
        testUpdate(givenContext(getUpdateContextBuilder()), HttpStatus.OK, givenResponse(getUpdateResponseBuilder()));
    }

    ModelBuilder<CONTEXT> getUpdateContextBuilder();

    ModelBuilder<RESPONSE> getUpdateResponseBuilder();

    default void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/update",
                HttpMethod.POST,
                context,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testUpdate(
                context,
                httpStatus,
                getDefaultMatcher(response, getIgnoredFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    default void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/update", HttpMethod.POST, context, matcher, httpStatus, responseClass);
    }

    default CONTEXT givenContext(ModelBuilder<CONTEXT> builder) {
        return builder.build();
    }

}
