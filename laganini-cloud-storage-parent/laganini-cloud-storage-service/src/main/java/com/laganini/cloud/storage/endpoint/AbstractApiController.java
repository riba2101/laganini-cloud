package com.laganini.cloud.storage.endpoint;

import com.laganini.cloud.exception.BusinessException;
import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.exception.detail.AbstractExceptionDetail;
import org.springframework.core.convert.converter.Converter;

import java.util.Arrays;

public abstract class AbstractApiController<MODEL, RESPONSE, SERVICE> {

    protected final SERVICE                    service;
    protected final Converter<MODEL, RESPONSE> outConverter;

    protected AbstractApiController(SERVICE service, Converter<MODEL, RESPONSE> outConverter) {
        this.service = service;
        this.outConverter = outConverter;
    }

    protected RESPONSE to(MODEL model) {
        return outConverter.convert(model);
    }

    protected BusinessException buildException(ExceptionType exceptionType, AbstractExceptionDetail... details) {
        return new BusinessException(exceptionType, Arrays.asList(details));
    }

}
