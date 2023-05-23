package org.laganini.cloud.data.audit.collector;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@ConfigurationProperties("laganini.storage.audit.collector")
public class LaganiniStorageAuditCollectorProperties {

    @NotNull
    private Integer queueSize     = 100;
    @NotNull
    private Integer bufferForInMs = 100;

}
