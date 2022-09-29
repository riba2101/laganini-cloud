package com.laganini.cloud.storage.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laganini.cloud.exception.BusinessExceptionResponse;
import com.laganini.cloud.storage.ModelBuilder;
import com.laganini.cloud.storage.connector.model.Fetchable;
import com.laganini.cloud.storage.connector.model.Filter;
import com.laganini.cloud.storage.connector.model.FilteredAndSorted;
import com.laganini.cloud.storage.connector.model.Id;
import com.laganini.cloud.storage.entity.IdentityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.function.Consumer;

public abstract class ReactiveFilterControllerTestSuite<
        ID,
        MODEL extends IdentityEntity<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends ReactiveApiControllerTestSuite<MODEL, RESPONSE>
{

    protected final ObjectMapper objectMapper;

    protected ReactiveFilterControllerTestSuite(
            ApplicationContext context,
            ObjectMapper objectMapper,
            String basePath
    )
    {
        super(context, basePath);

        this.objectMapper = objectMapper;
    }

    @Test
    protected void shouldFilter() {
        testFilter(givenFilter(getFilterBuilder()), HttpStatus.OK, givenFilteredResponse(getFilterResponseBuilder()));
    }

    protected abstract ModelBuilder<Filter> getFilterBuilder();

    protected abstract ModelBuilder<FilteredAndSorted<RESPONSE>> getFilterResponseBuilder();

    protected FilteredAndSorted<RESPONSE> givenFilteredResponse(ModelBuilder<FilteredAndSorted<RESPONSE>> builder) {
        return builder.build();
    }

    protected void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/filter",
                HttpMethod.POST,
                filter,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            FilteredAndSorted<RESPONSE> response
    )
    {
        Class<FilteredAndSorted<RESPONSE>> responseClass = (Class<FilteredAndSorted<RESPONSE>>) response.getClass();
        Class<RESPONSE> dataClass = response
                .getData()
                .isEmpty() ? null : (Class<RESPONSE>) response.getData().iterator().next().getClass();
        testFilter(filter, httpStatus, getFilterMatcher(response, dataClass), responseClass);
    }

    protected void testFilter(
            Filter filter,
            HttpStatus httpStatus,
            Consumer<FilteredAndSorted<RESPONSE>> matcher,
            Class<FilteredAndSorted<RESPONSE>> responseClass
    )
    {
        test("/filter", HttpMethod.POST, filter, matcher, httpStatus, responseClass);
    }

    protected Consumer<FilteredAndSorted<RESPONSE>> getFilterMatcher(
            FilteredAndSorted<RESPONSE> expected,
            Class<RESPONSE> dataClass
    )
    {
        return (v) -> {
            doAssert(expected, v, new String[]{"data"});

            doAssert(
                    expected.getData(),
                    objectMapper.convertValue(
                            v.getData(),
                            objectMapper
                                    .getTypeFactory()
                                    .constructCollectionType(Collection.class, dataClass)
                    ),
                    getGeneratedFields()
            );
        };
    }

    @Test
    protected void shouldFind() {
        testFind(givenId(getFindIdBuilder()), HttpStatus.OK, givenResponse(getFindResponseBuilder()));
    }

    protected abstract ModelBuilder<Id<ID>> getFindIdBuilder();

    protected abstract ModelBuilder<RESPONSE> getFindResponseBuilder();

    protected void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/find",
                HttpMethod.POST,
                id,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testFind(
                id,
                httpStatus,
                getDefaultMatcher(response, getGeneratedFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    protected void testFind(
            Id<ID> id,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/find", HttpMethod.POST, id, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldFindAll() {
        testFindAll(
                givenIds(getFindAllIdsBuilder()),
                HttpStatus.OK,
                givenCollectionResponse(getFindAllResponseBuilder())
        );
    }

    protected abstract ModelBuilder<Collection<Id<ID>>> getFindAllIdsBuilder();

    protected abstract ModelBuilder<Collection<RESPONSE>> getFindAllResponseBuilder();

    protected void testFindAll(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/find-by-ids",
                HttpMethod.POST,
                ids,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testFindAll(
            Collection<Id<ID>> ids,
            HttpStatus httpStatus,
            Collection<RESPONSE> responses
    )
    {
        Class<RESPONSE> dataClass = responses.isEmpty() ? null : (Class<RESPONSE>) responses
                .iterator()
                .next()
                .getClass();
        testFindAll(ids, getDefaultMatcher(responses, getGeneratedFields()), httpStatus, dataClass);
    }

    protected void testFindAll(
            Collection<Id<ID>> ids,
            Consumer<Collection<RESPONSE>> matcher,
            HttpStatus httpStatus,
            Class<RESPONSE> responseClass
    )
    {
        testCollection("/find-by-ids", HttpMethod.POST, ids, matcher, httpStatus, responseClass);
    }

    protected Collection<RESPONSE> givenCollectionResponse(ModelBuilder<Collection<RESPONSE>> builder) {
        return builder.build();
    }

    protected Filter givenFilter(ModelBuilder<Filter> builder) {
        return builder.build();
    }

    protected Id<ID> givenId(ModelBuilder<Id<ID>> builder) {
        return builder.build();
    }

    protected Collection<Id<ID>> givenIds(ModelBuilder<Collection<Id<ID>>> builder) {
        return builder.build();
    }

}
