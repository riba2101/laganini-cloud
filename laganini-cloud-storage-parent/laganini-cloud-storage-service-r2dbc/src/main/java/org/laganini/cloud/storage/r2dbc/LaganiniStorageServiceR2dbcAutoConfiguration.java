package org.laganini.cloud.storage.r2dbc;

import io.r2dbc.spi.ConnectionFactory;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.AbstractR2dbcQueryFactory;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.R2dbcConnectionProvider;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.mysql.MySqlR2dbcQueryFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FlywayProperties.class)
public class LaganiniStorageServiceR2dbcAutoConfiguration {

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

}
