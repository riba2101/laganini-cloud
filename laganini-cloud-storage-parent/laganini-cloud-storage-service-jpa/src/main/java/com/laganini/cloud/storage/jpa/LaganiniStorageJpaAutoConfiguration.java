package com.laganini.cloud.storage.jpa;

import com.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import com.laganini.cloud.storage.jpa.aop.CrudEntityAspect;
import com.laganini.cloud.storage.jpa.aop.JpaEntityAspect;
import com.laganini.cloud.storage.support.DateTimeParser;
import com.laganini.cloud.storage.support.DateTimeService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;
import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Primary
    JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    @ConditionalOnMissingBean
    CrudEntityAspect crudEntityAspect(EntityJoinPointHandler entityJoinPointHandler) {
        return new CrudEntityAspect(entityJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    JpaEntityAspect jpaEntityAspect(EntityJoinPointHandler entityJoinPointHandler) {
        return new JpaEntityAspect(entityJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    DateTimeParser dateTimeParser() {
        return new DateTimeParser();
    }

    @Bean
    @ConditionalOnMissingBean
    DateTimeService dateTimeService(Clock clock, DateTimeParser dateTimeParser) {
        return new DateTimeService(clock, dateTimeParser);
    }

}
