package org.laganini.cloud.storage.audit.configuration;

import org.laganini.cloud.logging.author.AuthorProvider;
import org.laganini.cloud.logging.author.DefaultAuthorProvider;
import org.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public abstract class AbstractLaganiniStorageAuditConfiguration
        extends AbstractLaganiniStorageAuditCommonConfiguration
{

    @Bean
    @ConditionalOnMissingBean
    AuditedRepositoryJoinPointHandler auditedRepositoryJoinPointHandler(
            RevisionService revisionService,
            RevisionEntryService revisionEntryService
    )
    {
        return new AuditedRepositoryJoinPointHandler(revisionService, revisionEntryService);
    }

    @Bean
    @ConditionalOnMissingBean
    AuthorProvider authorProvider() {
        return new DefaultAuthorProvider();
    }

}
