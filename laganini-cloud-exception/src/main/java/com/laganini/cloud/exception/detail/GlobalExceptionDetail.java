package com.laganini.cloud.exception.detail;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlobalExceptionDetail extends AbstractExceptionDetail {

    public GlobalExceptionDetail(String message, String code) {
        super(message, code);
    }

}
