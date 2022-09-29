package org.laganini.cloud.exception;

import org.laganini.cloud.exception.detail.AbstractExceptionDetail;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@ToString
public class BusinessExceptionResponse {

    private ExceptionType                       type;
    private Collection<AbstractExceptionDetail> details;

    private BusinessExceptionResponse() {
    }

    private BusinessExceptionResponse(ExceptionType type) {
        this(type, new ArrayList<>());
    }

    private BusinessExceptionResponse(ExceptionType type, Collection<AbstractExceptionDetail> details) {
        this.type = type;
        this.details = new ArrayList<>(details);
    }

    public static BusinessExceptionResponse.Builder builder(ExceptionType type) {
        return new BusinessExceptionResponse.Builder(type);
    }

    public static class Builder {

        private ExceptionType                       type;
        private Collection<AbstractExceptionDetail> details = new ArrayList<>();

        private Builder(ExceptionType type) {
            this.type = type;
        }

        public BusinessExceptionResponse.Builder type(ExceptionType type) {
            this.type = type;
            return this;
        }

        public BusinessExceptionResponse.Builder details(Collection<AbstractExceptionDetail> details) {
            this.details = details;
            return this;
        }

        public BusinessExceptionResponse.Builder detail(AbstractExceptionDetail detail) {
            this.details.add(detail);
            return this;
        }

        public BusinessExceptionResponse build() {
            return new BusinessExceptionResponse(type, details);
        }
    }

}
