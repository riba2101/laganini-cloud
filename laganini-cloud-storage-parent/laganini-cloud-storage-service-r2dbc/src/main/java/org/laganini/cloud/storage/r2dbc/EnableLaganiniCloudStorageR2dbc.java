package org.laganini.cloud.storage.r2dbc;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableTransactionManagement
@EnableR2dbcRepositories
public @interface EnableLaganiniCloudStorageR2dbc {

    @AliasFor(annotation = EnableR2dbcRepositories.class, attribute = "basePackages")
    String[] basePackages() default {};

}
