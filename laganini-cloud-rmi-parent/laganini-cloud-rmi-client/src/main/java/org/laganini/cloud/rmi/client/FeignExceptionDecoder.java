package org.laganini.cloud.rmi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.laganini.cloud.exception.BusinessException;
import org.laganini.cloud.exception.BusinessExceptionResponse;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignExceptionDecoder implements ErrorDecoder {

    private final ObjectMapper mapper = new ObjectMapper()
            .findAndRegisterModules();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            BusinessExceptionResponse businessExceptionResponse = mapper.readValue(
                    response.body().asInputStream(),
                    BusinessExceptionResponse.class
            );

            return BusinessException
                    .builder(businessExceptionResponse.getType())
                    .details(businessExceptionResponse.getDetails())
                    .build();
        } catch (Exception e) {
            log.error("Error mapping exception", e);
        }

        return FeignException.errorStatus(methodKey, response);
    }

}
