package org.laganini.cloud.storage.test;

import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.connector.model.Id;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public interface FindableControllerTestSuiteTrait<ID, RESPONSE>
        extends ControllerTestSuiteTrait<ID, RESPONSE>
{

    @Test
    default void shouldFind() {
        testFind(givenId(getFindIdBuilder()), HttpStatus.OK, givenResponse(getFindResponseBuilder()));
    }

    ModelBuilder<Id<ID>> getFindIdBuilder();

    ModelBuilder<RESPONSE> getFindResponseBuilder();

    default void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/find",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testFind(
                id,
                httpStatus,
                getDefaultMatcher(response, getIgnoredFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    default void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/find", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    default void shouldFindAll() {
        testFindAll(
                givenIds(getFindAllIdsBuilder()),
                HttpStatus.OK,
                givenCollectionResponse(getFindAllResponseBuilder())
        );
    }

    ModelBuilder<Collection<Id<ID>>> getFindAllIdsBuilder();

    ModelBuilder<Collection<RESPONSE>> getFindAllResponseBuilder();

    default void testFindAll(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/find-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getIgnoredFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    default void testFindAll(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Collection<RESPONSE> responses
    )
    {
        Class<RESPONSE> dataClass = responses.isEmpty() ? null : (Class<RESPONSE>) responses
                .iterator()
                .next()
                .getClass();
        testFindAll(ids, getDefaultMatcher(responses, getIgnoredFields()), httpStatus, dataClass);
    }

    default void testFindAll(
            Collection<Id<ID>> ids,
            Consumer<Collection<RESPONSE>> matcher,
            HttpStatus httpStatus,
            Class<RESPONSE> responseClass
    )
    {
        testCollection("/find-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    default Collection<RESPONSE> givenCollectionResponse(ModelBuilder<Collection<RESPONSE>> builder) {
        return builder.build();
    }

}
