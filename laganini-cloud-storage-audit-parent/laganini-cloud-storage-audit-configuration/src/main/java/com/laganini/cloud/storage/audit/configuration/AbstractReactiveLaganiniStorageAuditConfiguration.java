package com.laganini.cloud.storage.audit.configuration;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.logging.author.DefaultAuthorReactiveProvider;
import com.laganini.cloud.storage.audit.aop.handler.AuditedReactiveRepositoryJoinPointHandler;
import com.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import com.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public abstract class AbstractReactiveLaganiniStorageAuditConfiguration
        extends AbstractLaganiniStorageAuditCommonConfiguration
{

    @Bean
    @ConditionalOnMissingBean
    AuditedReactiveRepositoryJoinPointHandler auditedRepositoryReactiveJoinPointHandler(
            RevisionReactiveService revisionReactiveService,
            RevisionEntryReactiveService revisionEntryReactiveService
    )
    {
        return new AuditedReactiveRepositoryJoinPointHandler(revisionReactiveService, revisionEntryReactiveService);
    }

    @Bean
    @ConditionalOnMissingBean
    AuthorReactiveProvider authorReactiveProvider() {
        return new DefaultAuthorReactiveProvider();
    }

}
