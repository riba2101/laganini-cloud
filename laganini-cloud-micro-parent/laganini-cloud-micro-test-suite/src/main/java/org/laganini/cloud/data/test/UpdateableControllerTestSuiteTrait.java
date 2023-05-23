package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.UpdatableEndpoint;
import org.laganini.cloud.storage.connector.model.Fetchable;

import java.util.function.Consumer;

public interface UpdateableControllerTestSuiteTrait<
        ID,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>,
        TARGET extends UpdatableEndpoint<ID, CONTEXT, RESPONSE>
        >
        extends ControllerTestSuiteTrait<ID, RESPONSE, TARGET>
{

    @Test
    default void shouldUpdate() {
        testUpdate(givenContext(getUpdateContextBuilder()), givenResponse(getUpdateResponseBuilder()));
    }

    ModelBuilder<CONTEXT> getUpdateContextBuilder();

    ModelBuilder<RESPONSE> getUpdateResponseBuilder();

    default void testUpdate(
            CONTEXT context,
            BusinessExceptionResponse exception
    )
    {
        testUpdate(context, getDefaultMatcher(exception));
    }

    default void testUpdate(
            CONTEXT context,
            RESPONSE response
    )
    {
        testUpdate(context, getDefaultMatcher(response));
    }

    default void testUpdate(
            CONTEXT context,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().update(context), matcher);
    }

    default CONTEXT givenContext(ModelBuilder<CONTEXT> builder) {
        return builder.build();
    }

}
