package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.CreatebleEndpoint;
import org.laganini.cloud.storage.connector.model.Fetchable;

import java.util.function.Consumer;

public interface CreatableControllerTestSuiteTrait<
        ID,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>,
        TARGET extends CreatebleEndpoint<ID, CONTEXT, RESPONSE>
        >
        extends ControllerTestSuiteTrait<ID, RESPONSE, TARGET>
{

    @Test
    default void shouldCreate() {
        testCreate(givenContext(getCreateContextBuilder()), givenResponse(getCreateResponseBuilder()));
    }

    ModelBuilder<CONTEXT> getCreateContextBuilder();

    ModelBuilder<RESPONSE> getCreateResponseBuilder();

    default void testCreate(
            CONTEXT context,
            BusinessExceptionResponse exception
    )
    {
        testCreate(context, getDefaultMatcher(exception));
    }

    default void testCreate(
            CONTEXT context,
            RESPONSE response
    )
    {
        testCreate(context, getDefaultMatcher(response));
    }

    default void testCreate(
            CONTEXT context,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().create(context), matcher);
    }

    default CONTEXT givenContext(ModelBuilder<CONTEXT> builder) {
        return builder.build();
    }

}
