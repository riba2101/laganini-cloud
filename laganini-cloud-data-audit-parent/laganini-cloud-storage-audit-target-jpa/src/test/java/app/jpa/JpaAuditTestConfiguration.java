package app.jpa;

import org.laganini.cloud.observability.tracing.author.AuthorProvider;
import org.laganini.cloud.data.audit.target.jpa.EnableLaganiniCloudStorageAuditJpa;
import org.laganini.cloud.data.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages = "app.jpa")
@EnableLaganiniCloudStorageJpa(
        basePackages = {"app.jpa"},
        entityManagerFactoryRef = JpaAuditTestConfiguration.ENTITY_MANAGER_FACTORY_BEAN,
        transactionManagerRef = JpaAuditTestConfiguration.TRANSACTION_MANAGER_BEAN
)
@EnableLaganiniCloudStorageAuditJpa
public class JpaAuditTestConfiguration {

    static final String ENTITY_MANAGER_FACTORY_BEAN = "entityManagerFactory";
    static final String TRANSACTION_MANAGER_BEAN    = "transactionManager";

    @Bean
    AuthorProvider authorProvider() {
        return () -> "test";
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource dataSource() {
        return dataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY_BEAN)
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource,
            EntityManagerFactoryBuilder builder
    )
    {
        return builder
                .dataSource(dataSource)
                .packages("app.jpa")
                .build();
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER_BEAN)
    PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }


}
