package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

public interface CreatableControllerTestSuiteTrait<ID, CONTEXT extends Fetchable<ID>, RESPONSE extends Fetchable<ID>>
        extends ControllerTestSuiteTrait<ID, RESPONSE>
{

    @Test
    default void shouldCreate() {
        testCreate(givenContext(getCreateContextBuilder()), HttpStatus.OK, givenResponse(getCreateResponseBuilder()));
    }

    ModelBuilder<CONTEXT> getCreateContextBuilder();

    ModelBuilder<RESPONSE> getCreateResponseBuilder();

    default void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/create",
                HttpMethod.POST,
                context,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testCreate(
                context,
                httpStatus,
                getDefaultMatcher(response, getIgnoredFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    default void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/create", HttpMethod.POST, context, matcher, httpStatus, responseClass);
    }

    default CONTEXT givenContext(ModelBuilder<CONTEXT> builder) {
        return builder.build();
    }

}
