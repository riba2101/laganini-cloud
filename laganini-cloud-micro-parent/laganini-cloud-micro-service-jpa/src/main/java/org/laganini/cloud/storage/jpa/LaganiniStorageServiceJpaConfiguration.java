package org.laganini.cloud.storage.jpa;

import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageServiceJpaConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(JPAProvider.getTemplates(entityManager), entityManager);
    }

}
