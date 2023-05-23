package org.laganini.cloud.data.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.endpoint.PurgableEndpoint;
import org.laganini.cloud.storage.connector.model.Id;

import java.util.Collection;
import java.util.function.Consumer;

public interface PurgeableControllerTestSuiteTrait<
        ID,
        TARGET extends PurgableEndpoint<ID>
        >
        extends ControllerTestSuiteTrait<ID, Void, TARGET>
{

    @Test
    default void shouldMarkAsDeleted() {
        testMarkAsDeleted(givenId(getMarkAsDeletedIdBuilder()));
    }

    ModelBuilder<Id<ID>> getMarkAsDeletedIdBuilder();

    default void testMarkAsDeleted(
            Id<ID> id,
            BusinessExceptionResponse exception
    )
    {
        testMarkAsDeleted(id, getDefaultMatcher(exception));
    }

    default void testMarkAsDeleted(
            Id<ID> id
    )
    {
        testMarkAsDeleted(id, getVoidMatcher());
    }

    default void testMarkAsDeleted(
            Id<ID> id,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().markAsDeleted(id), matcher);
    }

    @Test
    default void shouldBatchMarkAsDeleted() {
        testBatchMarkAsDelete(givenIds(getMarkAsDeleteIdsBuilder()));
    }

    ModelBuilder<Collection<Id<ID>>> getMarkAsDeleteIdsBuilder();

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            BusinessExceptionResponse exception
    )
    {
        testBatchMarkAsDelete(ids, getDefaultMatcher(exception));
    }

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids
    )
    {
        testBatchMarkAsDelete(ids, getVoidMatcher());
    }

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().markAsDeleted(ids), matcher);
    }

    @Test
    default void shouldUnmarkAsDeleted() {
        testUnmarkAsDeleted(givenId(getUnmarkAsDeletedIdBuilder()));
    }

    ModelBuilder<Id<ID>> getUnmarkAsDeletedIdBuilder();

    default void testUnmarkAsDeleted(
            Id<ID> id,
            BusinessExceptionResponse exception
    )
    {
        testUnmarkAsDeleted(id, getDefaultMatcher(exception));
    }

    default void testUnmarkAsDeleted(
            Id<ID> id
    )
    {
        testUnmarkAsDeleted(id, getVoidMatcher());
    }

    default void testUnmarkAsDeleted(
            Id<ID> id,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().unmarkAsDeleted(id), matcher);
    }

    @Test
    default void shouldBatchUnmarkAsDeleted() {
        testBatchUnmarkAsDeleted(givenIds(getUnmarkAsDeleteIdsBuilder()));
    }

    ModelBuilder<Collection<Id<ID>>> getUnmarkAsDeleteIdsBuilder();

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            BusinessExceptionResponse exception
    )
    {
        testBatchUnmarkAsDeleted(ids, getDefaultMatcher(exception));
    }

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids
    )
    {
        testBatchUnmarkAsDeleted(ids, getVoidMatcher());
    }

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().unmarkAsDeleted(ids), matcher);
    }

    @Test
    default void shouldPurge() {
        testPurge(givenId(getPurgeIdBuilder()));
    }

    ModelBuilder<Id<ID>> getPurgeIdBuilder();

    default void testPurge(
            Id<ID> id,
            BusinessExceptionResponse exception
    )
    {
        testPurge(id, getDefaultMatcher(exception));
    }

    default void testPurge(
            Id<ID> id
    )
    {
        testPurge(id, getVoidMatcher());
    }

    default void testPurge(
            Id<ID> id,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().purge(id), matcher);
    }

    @Test
    default void shouldBatchPurge() {
        testBatchPurge(givenIds(getPurgeIdsBuilder()));
    }

    ModelBuilder<Collection<Id<ID>>> getPurgeIdsBuilder();

    default void testBatchPurge(
            Collection<Id<ID>> ids,
            BusinessExceptionResponse exception
    )
    {
        testBatchPurge(ids, getDefaultMatcher(exception));
    }

    default void testBatchPurge(
            Collection<Id<ID>> ids
    )
    {
        testBatchPurge(ids, getVoidMatcher());
    }

    default void testBatchPurge(
            Collection<Id<ID>> ids,
            Consumer<?> matcher
    )
    {
        test(getTargetApi().purge(ids), matcher);
    }

}
