package org.laganini.cloud.exception;

import lombok.Getter;

@Getter
public enum ExceptionType {

    REQUEST,
    RESPONSE,

    VALIDATION,

    AUTHENTICATION,
    AUTHORIZATION,

    ENTITY_FORBIDDEN,
    ENTITY_NOT_FOUND,

    METHOD_NOT_ALLOWED,

    UNSUPPORTED_MEDIA_TYPE,

    INTERNAL_SERVER_ERROR,

    API_NOT_FOUND,
    API_NOT_AVAILABLE,
    SERVICE_NOT_AVAILABLE

}
