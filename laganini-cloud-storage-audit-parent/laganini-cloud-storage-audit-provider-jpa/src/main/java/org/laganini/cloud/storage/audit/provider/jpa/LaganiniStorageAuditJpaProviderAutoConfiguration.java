package org.laganini.cloud.storage.audit.provider.jpa;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@AutoConfiguration
@AutoConfigureAfter(JpaRepositoriesAutoConfiguration.class)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "org.laganini.cloud.storage.audit.provider.jpa",
        entityManagerFactoryRef = LaganiniStorageAuditJpaProviderAutoConfiguration.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = LaganiniStorageAuditJpaProviderAutoConfiguration.TRANSACTION_MANAGER_BEAN
)
public class LaganiniStorageAuditJpaProviderAutoConfiguration {

    static final String ENTITY_MANAGER_FACTORY_BEAN = "auditEntityManagerFactory";
    static final String TRANSACTION_MANAGER_BEAN    = "auditTransactionManager";

    @Bean
    @ConfigurationProperties("laganini.storage.audit.jpa.datasource")
    DataSourceProperties auditDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    DataSource auditDataSource(DataSourceProperties auditDataSourceProperties) {
        return auditDataSourceProperties
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean auditEntityManagerFactory(
            DataSource auditDataSource,
            EntityManagerFactoryBuilder builder
    )
    {
        return builder
                .dataSource(auditDataSource)
                .packages("org.laganini.cloud.storage.audit.provider.jpa")
                .build();
    }

    @Bean(name = TRANSACTION_MANAGER_BEAN)
    PlatformTransactionManager auditTransactionManager(LocalContainerEntityManagerFactoryBean auditEntityManagerFactory) {
        return new JpaTransactionManager(auditEntityManagerFactory.getObject());
    }

}
