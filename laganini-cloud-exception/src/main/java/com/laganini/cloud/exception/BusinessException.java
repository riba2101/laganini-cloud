package org.laganini.cloud.exception;

import org.laganini.cloud.exception.detail.AbstractExceptionDetail;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BusinessException extends RuntimeException {

    private ExceptionType                       type;
    private Collection<AbstractExceptionDetail> details;

    public BusinessException(ExceptionType type) {
        this(type, Collections.emptyList());
    }

    public BusinessException(ExceptionType type, AbstractExceptionDetail... details) {
        this(type, Arrays.asList(details));
    }

    public BusinessException(ExceptionType type, Collection<AbstractExceptionDetail> details) {
        this.type = type;
        this.details = details;
    }

    public static Builder builder(ExceptionType type) {
        return new Builder(type);
    }

    public static class Builder {

        private ExceptionType                       type;
        private Collection<AbstractExceptionDetail> details = new ArrayList<>();

        private Builder(ExceptionType type) {
            this.type = type;
        }

        public Builder type(ExceptionType type) {
            this.type = type;
            return this;
        }

        public Builder details(Collection<AbstractExceptionDetail> details) {
            this.details = details;
            return this;
        }

        public Builder detail(AbstractExceptionDetail detail) {
            this.details.add(detail);
            return this;
        }

        public BusinessException build() {
            return new BusinessException(type, details);
        }

    }

}
