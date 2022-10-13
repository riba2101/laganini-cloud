package org.laganini.cloud.storage.r2dbc;

import org.laganini.cloud.storage.LaganiniStorageServiceAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableTransactionManagement
@EnableR2dbcRepositories
@EnableR2dbcAuditing
@ImportAutoConfiguration(classes = {
        LaganiniStorageServiceAutoConfiguration.class,
        LaganiniStorageServiceR2dbcAutoConfiguration.class
})
public @interface EnableLaganiniCloudStorageR2dbc {

    @AliasFor(annotation = EnableR2dbcRepositories.class, attribute = "basePackages")
    String[] basePackages() default {};

}
