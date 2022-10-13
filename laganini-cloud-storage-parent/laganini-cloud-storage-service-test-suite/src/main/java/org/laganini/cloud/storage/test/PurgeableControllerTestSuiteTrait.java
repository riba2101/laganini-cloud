package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public interface PurgeableControllerTestSuiteTrait<
        ID,
        MODEL extends IdentityEntity<ID> & OwnedEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends ControllerTestSuiteTrait<ID, RESPONSE>
{

    @Test
    default void shouldMarkAsDeleted() {
        testMarkAsDeleted(givenId(getMarkAsDeletedIdBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Id<ID>> getMarkAsDeletedIdBuilder();

    default void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/mark-as-deleted",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testMarkAsDeleted(id, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/mark-as-deleted", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldBatchMarkAsDeleted() {
        testBatchMarkAsDelete(givenIds(getMarkAsDeleteIdsBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Collection<Id<ID>>> getMarkAsDeleteIdsBuilder();

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/mark-as-deleted-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchMarkAsDelete(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/mark-as-deleted-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldUnmarkAsDeleted() {
        testUnmarkAsDeleted(givenId(getUnmarkAsDeletedIdBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Id<ID>> getUnmarkAsDeletedIdBuilder();

    default void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/unmark-as-deleted",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testUnmarkAsDeleted(id, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/unmark-as-deleted", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldBatchUnmarkAsDeleted() {
        testBatchUnmarkAsDeleted(givenIds(getUnmarkAsDeleteIdsBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Collection<Id<ID>>> getUnmarkAsDeleteIdsBuilder();

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/unmark-as-deleted-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchUnmarkAsDeleted(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/unmark-as-deleted-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldPurge() {
        testPurge(givenId(getPurgeIdBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Id<ID>> getPurgeIdBuilder();

    default void testPurge(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/purge",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testPurge(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testPurge(id, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testPurge(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/purge", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldBatchPurge() {
        testBatchPurge(givenIds(getPurgeIdsBuilder()), HttpStatus.OK);
    }

    ModelBuilder<Collection<Id<ID>>> getPurgeIdsBuilder();

    default void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/purge-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchPurge(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/purge-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

}
