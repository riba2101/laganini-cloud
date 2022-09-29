package com.laganini.cloud.storage.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laganini.cloud.exception.BusinessExceptionResponse;
import com.laganini.cloud.storage.ModelBuilder;
import com.laganini.cloud.storage.connector.model.Fetchable;
import com.laganini.cloud.storage.connector.model.Id;
import com.laganini.cloud.storage.entity.IdentityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class ReactiveCRUDControllerTestSuite<
        ID,
        MODEL extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends ReactiveCRUControllerTestSuite<ID, MODEL, CONTEXT, RESPONSE>
{

    protected ReactiveCRUDControllerTestSuite(
            ApplicationContext context,
            ObjectMapper objectMapper,
            String basePath
    )
    {
        super(context, objectMapper, basePath);
    }

    @Test
    protected void shouldDelete() {
        testDelete(givenId(getDeleteIdBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Id<ID>> getDeleteIdBuilder();

    protected void testDelete(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/delete",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testDelete(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testDelete(id, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testDelete(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/delete", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldBatchDelete() {
        testDelete(givenIds(getDeleteIdsBuilder()), HttpStatus.OK);
    }

    protected abstract ModelBuilder<Collection<Id<ID>>> getDeleteIdsBuilder();

    protected void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/delete-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testDelete(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    protected void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/delete-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

}
