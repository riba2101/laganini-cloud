package com.laganini.cloud.storage.service;

import com.laganini.cloud.exception.BusinessException;
import com.laganini.cloud.exception.ExceptionType;
import com.laganini.cloud.exception.detail.AbstractExceptionDetail;
import com.laganini.cloud.exception.detail.EntityExceptionDetail;
import com.laganini.cloud.exception.detail.code.registry.EntityCode;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
public abstract class AbstractService<ENTITY, REPO> {

    protected final REPO repository;

    protected AbstractService(REPO repository) {
        this.repository = repository;
    }

    protected BusinessException buildException(ExceptionType exceptionType, AbstractExceptionDetail... details) {
        return new BusinessException(exceptionType, Arrays.asList(details));
    }

    protected <T> Mono<ENTITY> buildEntityNotFound(T id) {
        return Mono.error(buildException(
                ExceptionType.ENTITY_NOT_FOUND,
                new EntityExceptionDetail("Entity not found", id, EntityCode.NOT_FOUND.getValue())
        ));
    }


    protected <T> Mono<ENTITY> buildEntityForbidden(T id) {
        return Mono.error(buildException(
                ExceptionType.ENTITY_FORBIDDEN,
                new EntityExceptionDetail("Entity forbidden", id, EntityCode.FORBIDDEN.getValue())
        ));
    }

}
