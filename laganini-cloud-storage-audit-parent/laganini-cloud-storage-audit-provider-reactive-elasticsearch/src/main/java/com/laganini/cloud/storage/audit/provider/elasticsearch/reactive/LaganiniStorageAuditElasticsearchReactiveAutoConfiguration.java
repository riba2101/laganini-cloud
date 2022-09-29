package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.storage.audit.aop.AuditedReactiveCrudRepositoryAspect;
import com.laganini.cloud.storage.audit.aop.handler.AuditedReactiveRepositoryJoinPointHandler;
import com.laganini.cloud.storage.audit.configuration.AbstractReactiveLaganiniStorageAuditConfiguration;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter.ReactiveElasticsearchRevisionConverter;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter.ReactiveElasticsearchRevisionEntryConverter;
import com.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import com.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageAuditElasticsearchReactiveAutoConfiguration
        extends AbstractReactiveLaganiniStorageAuditConfiguration
{

    @Bean
    @ConditionalOnMissingBean
    AuditedReactiveCrudRepositoryAspect auditedReactiveCrudRepositoryAspect(AuditedReactiveRepositoryJoinPointHandler auditedReactiveRepositoryJoinPointHandler) {
        return new AuditedReactiveCrudRepositoryAspect(auditedReactiveRepositoryJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionReactiveService elasticsearchReactiveRevisionReactiveService(ElasticsearchReactiveRevisionRepository repository) {
        return new ElasticsearchRevisionReactiveService(repository, new ReactiveElasticsearchRevisionConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionEntryReactiveService elasticsearchReactiveRevisionEntryReactiveService(
            ElasticsearchReactiveRevisionEntryRepository repository,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService,
            Clock clock
    )
    {
        return new ElasticsearchRevisionEntryReactiveService(
                repository,
                new ReactiveElasticsearchRevisionEntryConverter(),
                authorReactiveProvider,
                diffService,
                clock
        );
    }

}
