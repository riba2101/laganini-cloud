package org.laganini.cloud.exception.detail;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FieldExceptionDetail extends AbstractExceptionDetail {

    private String   path;
    private Object[] arguments;

    public FieldExceptionDetail(String message, String path, Object[] arguments, String code) {
        super(message, code);

        this.path = path;
        this.arguments = arguments;
    }

}
