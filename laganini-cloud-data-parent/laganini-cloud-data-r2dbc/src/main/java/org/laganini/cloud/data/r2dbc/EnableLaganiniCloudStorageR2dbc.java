package org.laganini.cloud.data.r2dbc;

import com.infobip.spring.data.r2dbc.EnableQuerydslR2dbcRepositories;
import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableTransactionManagement
@EnableR2dbcRepositories(repositoryFactoryBeanClass = QuerydslR2dbcRepositoryFactoryBean.class)
@ImportAutoConfiguration(LaganiniDataR2dbcAutoConfiguration.class)
public @interface EnableLaganiniCloudStorageR2dbc {

    @AliasFor(annotation = EnableR2dbcRepositories.class, attribute = "basePackages")
    String[] basePackages() default {};

    @AliasFor(annotation = EnableR2dbcRepositories.class, attribute = "entityOperationsRef")
    String entityOperationsRef() default "r2dbcEntityTemplate";

}
