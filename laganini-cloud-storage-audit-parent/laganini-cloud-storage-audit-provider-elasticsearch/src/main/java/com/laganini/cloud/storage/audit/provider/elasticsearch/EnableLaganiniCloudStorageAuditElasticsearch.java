package org.laganini.cloud.storage.audit.provider.elasticsearch;

import org.springframework.core.annotation.AliasFor;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableElasticsearchRepositories(basePackages = "org.laganini.cloud.storage.audit.provider.elasticsearch")
public @interface EnableLaganiniCloudStorageAuditElasticsearch {

    @AliasFor(annotation = EnableElasticsearchRepositories.class, attribute = "elasticsearchTemplateRef")
    String elasticsearchTemplateRef() default "elasticsearchTemplate";

}
