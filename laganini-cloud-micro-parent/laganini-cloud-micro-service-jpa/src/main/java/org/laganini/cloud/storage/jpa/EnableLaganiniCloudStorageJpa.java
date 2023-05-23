package org.laganini.cloud.storage.jpa;

import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepositoryFactoryBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = QuerydslJpaRepositoryFactoryBean.class)
@Import({LaganiniStorageServiceJpaConfiguration.class})
public @interface EnableLaganiniCloudStorageJpa {

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackages")
    String[] basePackages() default {};

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "entityManagerFactoryRef")
    String entityManagerFactoryRef() default "entityManagerFactory";

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "transactionManagerRef")
    String transactionManagerRef() default "transactionManager";

}
