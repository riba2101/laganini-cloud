package com.laganini.cloud.storage.audit.provider.r2dbc;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableR2dbcRepositories(basePackages = "com.laganini.cloud.storage.audit.provider.r2dbc")
public @interface EnableLaganiniCloudStorageAuditR2dbc {

    @AliasFor(annotation = EnableR2dbcRepositories.class, attribute = "entityOperationsRef")
    String entityOperationsRef() default "r2dbcEntityTemplate";

}
