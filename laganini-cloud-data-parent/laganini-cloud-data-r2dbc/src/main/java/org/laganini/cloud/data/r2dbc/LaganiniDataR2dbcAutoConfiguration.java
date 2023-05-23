package org.laganini.cloud.data.r2dbc;

import com.infobip.spring.data.r2dbc.R2dbcSQLTemplatesConfiguration;
import com.querydsl.sql.*;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.migration.JavaMigration;
import org.flywaydb.core.internal.jdbc.JdbcConnectionFactory;
import org.laganini.cloud.common.spring.EmptyObjectProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.DataSource;
import java.sql.SQLException;

@EnableConfigurationProperties(FlywayProperties.class)
@AutoConfigureBefore(R2dbcSQLTemplatesConfiguration.class)
@Configuration(proxyBeanMethods = false)
public class LaganiniDataR2dbcAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Flyway flyway(
            FlywayProperties properties,
            ResourceLoader resourceLoader,
            ObjectProvider<FlywayConfigurationCustomizer> fluentConfigurationCustomizers,
            ObjectProvider<JavaMigration> javaMigrations,
            ObjectProvider<Callback> callbacks
    )
    {
        FlywayAutoConfiguration.FlywayConfiguration flywayConfiguration = new FlywayAutoConfiguration.FlywayConfiguration();
        return flywayConfiguration.flyway(
                properties,
                resourceLoader,
                new EmptyObjectProvider<>(),
                new EmptyObjectProvider<>(),
                fluentConfigurationCustomizers,
                javaMigrations,
                callbacks
        );
    }

    @ConditionalOnBean(Flyway.class)
    @Bean
    public SQLTemplates sqlTemplates(Flyway flyway) throws SQLException {
        var jdbcConnectionFactory = new JdbcConnectionFactory(
                flyway.getConfiguration().getDataSource(),
                flyway.getConfiguration(),
                null
        );
        var sqlTemplatesRegistry = new SQLTemplatesRegistry();
        var metaData             = jdbcConnectionFactory.openConnection().getMetaData();

        var templates = sqlTemplatesRegistry.getTemplates(metaData);

        if (templates instanceof SQLServerTemplates && metaData.getDatabaseMajorVersion() > 11) {
            return new SQLServer2012Templates();
        }

        return templates;
    }

    @ConditionalOnMissingBean
    @Bean
    public com.querydsl.sql.Configuration querydslSqlConfiguration(SQLTemplates sqlTemplates) {
        var configuration = new com.querydsl.sql.Configuration(sqlTemplates);
        configuration.setUseLiterals(true);
        return configuration;
    }

    @ConditionalOnMissingBean
    @Bean
    public SQLQueryFactory sqlQueryFactory(com.querydsl.sql.Configuration querydslSqlConfiguration) {
        DataSource dataSource = null;
        return new SQLQueryFactory(querydslSqlConfiguration, dataSource);
    }

}
