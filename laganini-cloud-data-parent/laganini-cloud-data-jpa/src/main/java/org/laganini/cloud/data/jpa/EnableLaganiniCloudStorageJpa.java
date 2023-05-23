package org.laganini.cloud.data.jpa;

import com.infobip.spring.data.jpa.ExtendedQuerydslJpaRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableTransactionManagement
@EnableJpaRepositories(repositoryFactoryBeanClass = ExtendedQuerydslJpaRepositoryFactoryBean.class)
@ImportAutoConfiguration(LaganiniDataJpaAutoConfiguration.class)
public @interface EnableLaganiniCloudStorageJpa {

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackages")
    String[] basePackages() default {};

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "entityManagerFactoryRef")
    String entityManagerFactoryRef() default "entityManagerFactory";

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "transactionManagerRef")
    String transactionManagerRef() default "transactionManager";

}
