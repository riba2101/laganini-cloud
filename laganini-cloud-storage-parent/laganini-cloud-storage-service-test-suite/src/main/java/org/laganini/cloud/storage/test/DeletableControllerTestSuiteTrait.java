package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.connector.model.Id;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public interface DeletableControllerTestSuiteTrait<ID, RESPONSE extends Fetchable<ID>>
        extends ControllerTestSuiteTrait<ID,RESPONSE>
{

    @Test
    default void shouldDelete() {
        testDelete(givenId(getDeleteIdBuilder()), HttpStatus.OK);
    }

     ModelBuilder<Id<ID>> getDeleteIdBuilder();

    default void testDelete(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/delete",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testDelete(
            Id<ID> id,
            HttpStatus httpStatus
    )
    {
        testDelete(id, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testDelete(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/delete", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldBatchDelete() {
        testDelete(givenIds(getDeleteIdsBuilder()), HttpStatus.OK);
    }

     ModelBuilder<Collection<Id<ID>>> getDeleteIdsBuilder();

    default void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/delete-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus
    )
    {
        testDelete(ids, httpStatus, getVoidMatcher(), Void.class);
    }

    default void testDelete(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Consumer<Void> matcher,
            Class<Void> responseClass
    )
    {
        test("/delete-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }


}
