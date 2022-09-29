package com.laganini.cloud.storage.audit.provider.r2dbc;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.storage.audit.aop.AuditedReactiveCrudRepositoryAspect;
import com.laganini.cloud.storage.audit.aop.handler.AuditedReactiveRepositoryJoinPointHandler;
import com.laganini.cloud.storage.audit.configuration.AbstractReactiveLaganiniStorageAuditConfiguration;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.provider.r2dbc.converter.R2dbcRevisionConverter;
import com.laganini.cloud.storage.audit.provider.r2dbc.converter.R2dbcRevisionEntryConverter;
import com.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import com.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageAuditR2dbcAutoConfiguration extends AbstractReactiveLaganiniStorageAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    AuditedReactiveCrudRepositoryAspect auditedReactiveCrudRepositoryAspect(AuditedReactiveRepositoryJoinPointHandler auditedReactiveRepositoryJoinPointHandler) {
        return new AuditedReactiveCrudRepositoryAspect(auditedReactiveRepositoryJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionReactiveService r2dbcRevisionReactiveService(R2dbcReactiveRevisionRepository repository) {
        return new R2dbcRevisionReactiveService(repository, new R2dbcRevisionConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionEntryReactiveService r2dbcRevisionEntryReactiveService(
            R2dbcReactiveRevisionEntryRepository repository,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService,
            Clock clock
    )
    {
        return new R2dbcRevisionEntryReactiveService(
                repository,
                new R2dbcRevisionEntryConverter(),
                authorReactiveProvider,
                diffService,
                clock
        );
    }

}
