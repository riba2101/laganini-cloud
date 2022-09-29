package org.laganini.cloud.storage.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.ModelBuilder;
import org.laganini.cloud.storage.connector.model.AbstractOwnedBase;
import org.laganini.cloud.storage.connector.model.AbstractOwnedContext;
import org.laganini.cloud.storage.connector.model.Id;
import org.laganini.cloud.storage.entity.OwnedEntity;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class ReactivePurgeableControllerTestSuite<
        ID,
        MODEL extends OwnedEntity<ID>,
        CONTEXT extends AbstractOwnedContext<ID>,
        RESPONSE extends AbstractOwnedBase<ID>
        >
        extends ReactiveOwnedControllerTestSuite<ID, MODEL, CONTEXT, RESPONSE>
{

    protected ReactivePurgeableControllerTestSuite(
            ApplicationContext context,
            ObjectMapper objectMapper,
            String basePath
    )
    {
        super(context, objectMapper, basePath);
    }

    @Test
    protected void shouldMarkAsDeleted() {
        testMarkAsDeleted(givenId(getMarkAsDeletedIdBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Id<ID>> getMarkAsDeletedIdBuilder();

    protected void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/mark-as-deleted",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testMarkAsDeleted(id, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testMarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/mark-as-deleted", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldBatchMarkAsDeleted() {
        testBatchMarkAsDelete(givenIds(getMarkAsDeleteIdsBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Collection<Id<ID>>> getMarkAsDeleteIdsBuilder();

    protected void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/mark-as-deleted-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchMarkAsDelete(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testBatchMarkAsDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/mark-as-deleted-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldUnmarkAsDeleted() {
        testUnmarkAsDeleted(givenId(getUnmarkAsDeletedIdBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Id<ID>> getUnmarkAsDeletedIdBuilder();

    protected void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/unmark-as-deleted",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testUnmarkAsDeleted(id, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testUnmarkAsDeleted(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/unmark-as-deleted", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldBatchUnmarkAsDeleted() {
        testBatchUnmarkAsDeleted(givenIds(getUnmarkAsDeleteIdsBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Collection<Id<ID>>> getUnmarkAsDeleteIdsBuilder();

    protected void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/unmark-as-deleted-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchUnmarkAsDeleted(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testBatchUnmarkAsDeleted(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/unmark-as-deleted-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldPurge() {
        testPurge(givenId(getPurgeIdBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Id<ID>> getPurgeIdBuilder();

    protected void testPurge(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/purge",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testPurge(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testPurge(id, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testPurge(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/purge", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldBatchPurge() {
        testBatchPurge(givenIds(getPurgeIdsBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Collection<Id<ID>>> getPurgeIdsBuilder();

    protected void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/purge-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testBatchPurge(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testBatchPurge(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/purge-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

}
