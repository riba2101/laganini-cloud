package org.laganini.cloud.data.jpa;

import com.infobip.spring.data.jpa.JPASQLQueryFactory;
import com.querydsl.jpa.impl.JPAProvider;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.SQLTemplatesRegistry;
import jakarta.persistence.EntityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Configuration(proxyBeanMethods = false)
public class LaganiniDataJpaAutoConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public SQLTemplates sqlTemplates(DataSource dataSource) throws SQLException {
        SQLTemplatesRegistry              sqlTemplatesRegistry = new SQLTemplatesRegistry();
        DatabaseMetaData metaData;
        try (Connection connection = dataSource.getConnection()) {
            metaData = connection.getMetaData();
        }

        return sqlTemplatesRegistry.getTemplates(metaData);
    }

    @ConditionalOnMissingBean
    @Bean
    public JPASQLQueryFactory jpaSqlQueryFactory(EntityManager entityManager, SQLTemplates sqlTemplates) {
        return () -> new JPASQLQuery<>(entityManager, sqlTemplates);
    }

    @ConditionalOnMissingBean
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(JPAProvider.getTemplates(entityManager), entityManager);
    }

}
