package org.laganini.cloud.storage.r2dbc;

import org.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import org.laganini.cloud.storage.r2dbc.aop.ReactiveEntityAspect;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.AbstractR2dbcQueryFactory;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.R2dbcConnectionProvider;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.mysql.MySqlR2dbcQueryFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FlywayProperties.class)
public class LaganiniStorageR2dbcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    R2dbcConnectionProvider r2dbcConnectionProvider(ConnectionFactory connectionFactory) {
        return () -> Mono.from(connectionFactory.create());
    }

    @Bean
    @ConditionalOnMissingBean
    AbstractR2dbcQueryFactory r2dbcQueryFactory(R2dbcConnectionProvider r2dbcConnectionProvider) {
        return new MySqlR2dbcQueryFactory(r2dbcConnectionProvider);
    }

    @Bean
    @ConditionalOnMissingBean
    ReactiveEntityAspect reactiveEntityAspect(EntityJoinPointHandler entityJoinPointHandler) {
        return new ReactiveEntityAspect(entityJoinPointHandler);
    }

    @Bean
    @ConditionalOnMissingBean
    Flyway flyway(FlywayProperties flywayProperties) {
        return Flyway
                .configure()
                .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
                .cleanOnValidationError(flywayProperties.isCleanOnValidationError())
                .dataSource(flywayProperties.getUrl(), flywayProperties.getUser(), flywayProperties.getPassword())
                .load();
    }

    @Bean
    @ConditionalOnMissingBean
    FlywayMigrationInitializer flywayMigrationInitializer(Flyway flyway) {
        return new FlywayMigrationInitializer(flyway);
    }

}
