package com.laganini.cloud.storage.audit.provider.jpa;

import com.laganini.cloud.logging.author.AuthorProvider;
import com.laganini.cloud.storage.audit.aop.AuditedCrudRepositoryAspect;
import com.laganini.cloud.storage.audit.aop.AuditedJpaRepositoryAspect;
import com.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;
import com.laganini.cloud.storage.audit.configuration.AbstractLaganiniStorageAuditConfiguration;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.provider.jpa.converter.JpaRevisionConverter;
import com.laganini.cloud.storage.audit.provider.jpa.converter.JpaRevisionEntryConverter;
import com.laganini.cloud.storage.audit.service.RevisionEntryService;
import com.laganini.cloud.storage.audit.service.RevisionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageAuditJpaAutoConfiguration extends AbstractLaganiniStorageAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    RevisionService jpaRevisionService(JpaRevisionRepository repository) {
        return new JpaRevisionService(repository, new JpaRevisionConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionEntryService jpaRevisionEntryService(
            JpaRevisionEntryRepository repository,
            AuthorProvider authorProvider,
            DiffService diffService,
            Clock clock
    )
    {
        return new JpaRevisionEntryService(
                repository,
                new JpaRevisionEntryConverter(),
                authorProvider,
                diffService,
                clock
        );
    }

    @Bean
    @ConditionalOnMissingBean
    AuditedCrudRepositoryAspect jpaAuditedCrudRepositoryAspect(AuditedRepositoryJoinPointHandler auditedRepositoryJoinPointHandler) {
        return new AuditedCrudRepositoryAspect(auditedRepositoryJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    AuditedJpaRepositoryAspect jpaAuditedJpaRepositoryAspect(AuditedRepositoryJoinPointHandler auditedRepositoryJoinPointHandler) {
        return new AuditedJpaRepositoryAspect(auditedRepositoryJoinPointHandler);
    }

}
