package org.laganini.cloud.storage.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.storage.ModelBuilder;
import org.laganini.cloud.storage.connector.model.Fetchable;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.function.Consumer;

public abstract class ReactiveCRUControllerTestSuite<
        ID,
        MODEL extends IdentityEntity<ID>,
        CONTEXT extends Fetchable<ID>,
        RESPONSE extends Fetchable<ID>
        >
        extends ReactiveFilterControllerTestSuite<ID, MODEL, RESPONSE>
{

    protected ReactiveCRUControllerTestSuite(
            ApplicationContext context,
            ObjectMapper objectMapper,
            String basePath
    )
    {
        super(context, objectMapper, basePath);
    }

    @Test
    protected void shouldCreate() {
        testCreate(givenContext(getCreateContextBuilder()), HttpStatus.OK, givenResponse(getCreateResponseBuilder()));
    }

    protected abstract ModelBuilder<CONTEXT> getCreateContextBuilder();

    protected abstract ModelBuilder<RESPONSE> getCreateResponseBuilder();

    protected void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/create",
                HttpMethod.POST,
                context,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testCreate(
                context,
                httpStatus,
                getDefaultMatcher(response, getGeneratedFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    protected void testCreate(
            CONTEXT context,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/create", HttpMethod.POST, context, matcher, httpStatus, responseClass);
    }

    @Test
    protected void shouldUpdate() {
        testUpdate(givenContext(getUpdateContextBuilder()), HttpStatus.OK, givenResponse(getUpdateResponseBuilder()));
    }

    protected abstract ModelBuilder<CONTEXT> getUpdateContextBuilder();

    protected abstract ModelBuilder<RESPONSE> getUpdateResponseBuilder();

    protected void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            BusinessExceptionResponse exception
    )
    {
        test(
                "/update",
                HttpMethod.POST,
                context,
                getDefaultMatcher(exception, getGeneratedFields()),
                httpStatus,
                BusinessExceptionResponse.class
        );
    }

    protected void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            RESPONSE response
    )
    {
        testUpdate(
                context,
                httpStatus,
                getDefaultMatcher(response, getGeneratedFields()),
                (Class<RESPONSE>) response.getClass()
        );
    }

    protected void testUpdate(
            CONTEXT context,
            HttpStatus httpStatus,
            Consumer<RESPONSE> matcher,
            Class<RESPONSE> responseClass
    )
    {
        test("/update", HttpMethod.POST, context, matcher, httpStatus, responseClass);
    }

    protected CONTEXT givenContext(ModelBuilder<CONTEXT> builder) {
        return builder.build();
    }

}
