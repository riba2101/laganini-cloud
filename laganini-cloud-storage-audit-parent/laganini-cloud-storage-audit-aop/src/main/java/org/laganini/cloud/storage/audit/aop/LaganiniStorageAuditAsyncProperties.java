package org.laganini.cloud.storage.audit.aop;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LaganiniStorageAuditAsyncProperties {

    @NotNull
    private Integer threadPoolCount     = 10;
    @NotNull
    private Integer threadPoolQueueSize = 100;

}
