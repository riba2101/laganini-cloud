package org.laganini.cloud.storage.audit.configuration;

import org.laganini.cloud.storage.audit.diff.DefaultDiffService;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public abstract class AbstractLaganiniStorageAuditCommonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    DiffService diffService() {
        return new DefaultDiffService();
    }

}
