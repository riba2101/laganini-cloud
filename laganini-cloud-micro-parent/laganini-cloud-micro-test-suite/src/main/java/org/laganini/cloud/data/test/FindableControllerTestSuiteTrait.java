package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.FindableEndpoint;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;

import java.util.Collection;
import java.util.function.Consumer;

public interface FindableControllerTestSuiteTrait<
        ID,
        RESPONSE extends Fetchable<ID>,
        TARGET extends FindableEndpoint<ID, RESPONSE>
        >
        extends ControllerTestSuiteTrait<ID, RESPONSE, TARGET>
{

    @Test
    default void shouldFind() {
        testFind(givenId(getFindIdBuilder()), givenResponse(getFindResponseBuilder()));
    }

    ModelBuilder<Id<ID>> getFindIdBuilder();

    ModelBuilder<RESPONSE> getFindResponseBuilder();

    default void testFind(
            Id<ID> id,
            BusinessExceptionResponse exception
    )
    {
        testFind(id, getDefaultMatcher(exception));
    }

    default void testFind(
            Id<ID> id,
            RESPONSE response
    )
    {
        testFind(id, getDefaultMatcher(response));
    }

    default void testFind(
            Id<ID> id,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().find(id), matcher);
    }

    @Test
    default void shouldFindAll() {
        testFindAll(
                givenIds(getFindAllIdsBuilder()),
                givenCollectionResponse(getFindAllResponseBuilder())
        );
    }

    ModelBuilder<Collection<Id<ID>>> getFindAllIdsBuilder();

    ModelBuilder<Collection<RESPONSE>> getFindAllResponseBuilder();

    default void testFindAll(
            Collection<Id<ID>> ids,
            BusinessExceptionResponse exception
    )
    {
        testFindAll(ids, getDefaultMatcher(exception));
    }

    default void testFindAll(
            Collection<Id<ID>> ids,
            Collection<RESPONSE> responses
    )
    {
        testFindAll(ids, getDefaultMatcher(responses));
    }

    default void testFindAll(
            Collection<Id<ID>> ids,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().findAll(ids), matcher);
    }

    default Collection<RESPONSE> givenCollectionResponse(ModelBuilder<Collection<RESPONSE>> builder) {
        return builder.build();
    }

}
