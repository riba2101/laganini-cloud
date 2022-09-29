package org.laganini.cloud.rmi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.FieldExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.BeanValidationCode;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import test.app.*;

@IntegrationTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(initializers = TestcontainersContextInitializer.class, classes = TestConfiguration.class)
class ServerControllerAdviceIT {

    @LocalServerPort
    private int              port;
    @Autowired
    private TestRepository   testRepository;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper     objectMapper;

    @Test
    void shouldHandleConstraintException() throws JsonProcessingException {
        TestBody request = givenTestBody(null);

        String body = restTemplate.postForObject(
                "http://localhost:" + port + "/" + TestEndpoint.PATH,
                request,
                String.class
        );
        BusinessExceptionResponse businessExceptionResponse = objectMapper.readValue(
                body,
                BusinessExceptionResponse.class
        );

        Assertions
                .assertThat(businessExceptionResponse.getType())
                .isEqualTo(ExceptionType.VALIDATION);
        Assertions
                .assertThat(businessExceptionResponse.getDetails())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(
                        new FieldExceptionDetail(
                                "must not be blank",
                                "name",
                                null,
                                BeanValidationCode.NOT_BLANK.getValue()
                        ),
                        new FieldExceptionDetail(
                                "must not be null",
                                "id",
                                null,
                                BeanValidationCode.NOT_NULL.getValue()
                        )
                );
    }

    @Test
    void shouldHandleBindExceptionOnMethod() throws JsonProcessingException {
        TestBody request = givenTestBody(null);

        String body = restTemplate.postForObject(
                "http://localhost:" + port + "/" + TestEndpoint.PATH,
                null,
                String.class
        );
        BusinessExceptionResponse businessExceptionResponse = objectMapper.readValue(
                body,
                BusinessExceptionResponse.class
        );

        Assertions
                .assertThat(businessExceptionResponse.getType())
                .isEqualTo(ExceptionType.REQUEST);
        Assertions
                .assertThat(businessExceptionResponse.getDetails())
                .isEmpty();
    }

    @Test
    void shouldHandleBindExceptionOnBody() throws JsonProcessingException {
        TestBody request = givenTestBody(1L);
        request.setName(null);

        String body = restTemplate.postForObject(
                "http://localhost:" + port + "/" + TestEndpoint.PATH,
                request,
                String.class
        );
        BusinessExceptionResponse businessExceptionResponse = objectMapper.readValue(
                body,
                BusinessExceptionResponse.class
        );

        Assertions
                .assertThat(businessExceptionResponse.getType())
                .isEqualTo(ExceptionType.VALIDATION);
        Assertions
                .assertThat(businessExceptionResponse.getDetails())
                .usingRecursiveFieldByFieldElementComparator()
                .contains(
                        new FieldExceptionDetail(
                                "must not be blank",
                                "name",
                                null,
                                BeanValidationCode.NOT_BLANK.getValue()
                        )
                );
    }

    private TestBody givenTestBody(Long id) {
        return new TestBody(id, "test" + id);
    }

    private TestEntity givenTestEntity(Long id) {
        return new TestEntity(id, "test" + id);
    }

}