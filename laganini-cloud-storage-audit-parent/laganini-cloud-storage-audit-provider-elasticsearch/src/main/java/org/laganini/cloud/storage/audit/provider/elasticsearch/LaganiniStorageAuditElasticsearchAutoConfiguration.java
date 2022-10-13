package org.laganini.cloud.storage.audit.provider.elasticsearch;

import org.laganini.cloud.logging.author.AuthorProvider;
import org.laganini.cloud.storage.audit.aop.AuditedCrudRepositoryAspect;
import org.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;
import org.laganini.cloud.storage.audit.configuration.AbstractLaganiniStorageAuditConfiguration;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionConverter;
import org.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionEntryConverter;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;

import java.time.Clock;

@AutoConfiguration
public class LaganiniStorageAuditElasticsearchAutoConfiguration extends AbstractLaganiniStorageAuditConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ElasticsearchRevisionRepository elasticsearchRevisionRepository(ElasticsearchOperations elasticsearchOperations) {
        ElasticsearchRepositoryFactory elasticsearchRepositoryFactory = new ElasticsearchRepositoryFactory(
                elasticsearchOperations);
        return elasticsearchRepositoryFactory.getRepository(ElasticsearchRevisionRepository.class);

    }

    @Bean
    @ConditionalOnMissingBean
    ElasticsearchRevisionEntryRepository elasticsearchRevisionEntryRepository(ElasticsearchOperations elasticsearchOperations) {
        ElasticsearchRepositoryFactory elasticsearchRepositoryFactory = new ElasticsearchRepositoryFactory(
                elasticsearchOperations);
        return elasticsearchRepositoryFactory.getRepository(ElasticsearchRevisionEntryRepository.class);
    }

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
