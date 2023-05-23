package org.laganini.cloud.rmi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.FieldExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.BeanValidationCode;
import org.laganini.cloud.test.integration.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import test.app.TestBody;
import test.app.TestConfiguration;
import test.app.TestEndpoint;

@IntegrationTest(classes = TestConfiguration.class)
class ServerControllerAdviceIT {

    @LocalServerPort
    private int              port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper     objectMapper;

    @Test
    void shouldHandleBindExceptionOnMethod() throws JsonProcessingException {
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
                        )
                );
    }

    private TestBody givenTestBody(String name) {
        return new TestBody(null, name);
    }

}