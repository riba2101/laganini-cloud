package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableReactiveElasticsearchRepositories(basePackages = "com.laganini.cloud.storage.audit.provider.elasticsearch.reactive")
public @interface EnableLaganiniCloudStorageAuditElasticsearchReactive {

    @AliasFor(annotation = EnableReactiveElasticsearchRepositories.class,
              attribute = "reactiveElasticsearchTemplateRef")
    String reactiveElasticsearchTemplateRef() default "reactiveElasticsearchTemplate";

}
