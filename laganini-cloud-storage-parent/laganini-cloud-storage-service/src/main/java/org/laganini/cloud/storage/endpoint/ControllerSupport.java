package org.laganini.cloud.storage.endpoint;

import org.laganini.cloud.exception.BusinessException;
import org.laganini.cloud.exception.ExceptionType;
import org.laganini.cloud.exception.detail.AbstractExceptionDetail;
import org.laganini.cloud.exception.detail.EntityExceptionDetail;
import org.laganini.cloud.exception.detail.code.registry.EntityCode;

import java.util.Arrays;

public interface ControllerSupport<ID> {

    default BusinessException buildException(ExceptionType exceptionType, AbstractExceptionDetail... details) {
        return new BusinessException(exceptionType, Arrays.asList(details));
    }

    default BusinessException buildEntityNotFound(ID id) {
        return buildException(
                ExceptionType.ENTITY_NOT_FOUND,
                new EntityExceptionDetail<>("Entity not found", id, EntityCode.NOT_FOUND.getValue())
        );
    }

    default BusinessException buildEntityForbidden(ID id) {
        return buildException(
                ExceptionType.ENTITY_FORBIDDEN,
                new EntityExceptionDetail<>("Entity forbidden", id, EntityCode.FORBIDDEN.getValue())
        );
    }

}
