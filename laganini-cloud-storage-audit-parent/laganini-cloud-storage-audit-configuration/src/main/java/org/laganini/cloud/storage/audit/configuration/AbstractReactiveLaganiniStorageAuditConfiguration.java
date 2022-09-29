package org.laganini.cloud.storage.audit.configuration;

import org.laganini.cloud.logging.author.AuthorReactiveProvider;
import org.laganini.cloud.logging.author.DefaultAuthorReactiveProvider;
import org.laganini.cloud.storage.audit.aop.handler.AuditedReactiveRepositoryJoinPointHandler;
import org.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import org.laganini.cloud.storage.audit.service.RevisionReactiveService;
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
