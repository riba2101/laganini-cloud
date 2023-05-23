package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.DeletableEndpoint;
import org.laganini.cloud.storage.connector.model.Id;

import java.util.Collection;
import java.util.function.Consumer;

public interface DeletableControllerTestSuiteTrait<
        ID,
        TARGET extends DeletableEndpoint<ID>
        >
        extends ControllerTestSuiteTrait<ID, Void, TARGET>
{

    @Test
    default void shouldDelete() {
        testDelete(givenId(getDeleteIdBuilder()));
    }

    ModelBuilder<Id<ID>> getDeleteIdBuilder();

    default void testDelete(
            Id<ID> id,
            BusinessExceptionResponse exception
    )
    {
        testDelete(id, getDefaultMatcher(exception));
    }

    default void testDelete(
            Id<ID> id
    )
    {
        testDelete(id, getVoidMatcher());
    }

    default void testDelete(
            Id<ID> id,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().delete(id), matcher);
    }

    @Test
    default void shouldBatchDelete() {
        testDelete(givenIds(getDeleteIdsBuilder()));
    }

    ModelBuilder<Collection<Id<ID>>> getDeleteIdsBuilder();

    default void testDelete(
            Collection<Id<ID>> ids,
            BusinessExceptionResponse exception
    )
    {
        testDelete(ids, getDefaultMatcher(exception));
    }

    default void testDelete(
            Collection<Id<ID>> ids
    )
    {
        testDelete(ids, getVoidMatcher());
    }

    default void testDelete(
            Collection<Id<ID>> ids,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().delete(ids), matcher);
    }


}
