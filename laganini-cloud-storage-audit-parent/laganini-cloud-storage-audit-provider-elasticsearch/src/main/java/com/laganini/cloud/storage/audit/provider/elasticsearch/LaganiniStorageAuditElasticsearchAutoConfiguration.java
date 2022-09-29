package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.logging.author.AuthorProvider;
import com.laganini.cloud.storage.audit.aop.AuditedCrudRepositoryAspect;
import com.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;
import com.laganini.cloud.storage.audit.configuration.AbstractLaganiniStorageAuditConfiguration;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionConverter;
import com.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionEntryConverter;
import com.laganini.cloud.storage.audit.service.RevisionEntryService;
import com.laganini.cloud.storage.audit.service.RevisionService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageAuditElasticsearchAutoConfiguration extends AbstractLaganiniStorageAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    RevisionService elasticsearchRevisionService(ElasticsearchRevisionRepository repository) {
        return new ElasticsearchRevisionService(repository, new ElasticsearchRevisionConverter());
    }

    @Bean
    @ConditionalOnMissingBean
    RevisionEntryService elasticsearchRevisionEntryService(
            ElasticsearchRevisionEntryRepository repository,
            AuthorProvider authorProvider,
            DiffService diffService,
            Clock clock
    )
    {
        return new ElasticsearchRevisionEntryService(
                repository,
                new ElasticsearchRevisionEntryConverter(),
                authorProvider,
                diffService,
                clock
        );
    }

    @Bean
    @ConditionalOnMissingBean
    AuditedCrudRepositoryAspect elasticsearchAuditedCrudRepositoryAspect(AuditedRepositoryJoinPointHandler auditedRepositoryJoinPointHandler) {
        return new AuditedCrudRepositoryAspect(auditedRepositoryJoinPointHandler);
    }

}
