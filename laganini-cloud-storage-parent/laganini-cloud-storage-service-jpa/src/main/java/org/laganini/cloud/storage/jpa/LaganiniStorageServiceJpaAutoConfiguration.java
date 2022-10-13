package org.laganini.cloud.storage.jpa;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.laganini.cloud.storage.LaganiniStorageServiceAutoConfiguration;
import org.laganini.cloud.storage.support.DateTimeParser;
import org.laganini.cloud.storage.support.DateTimeService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.persistence.EntityManager;
import java.time.Clock;

@AutoConfiguration(after = {HibernateJpaAutoConfiguration.class, LaganiniStorageServiceAutoConfiguration.class})
class LaganiniStorageServiceJpaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Primary
    JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
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
