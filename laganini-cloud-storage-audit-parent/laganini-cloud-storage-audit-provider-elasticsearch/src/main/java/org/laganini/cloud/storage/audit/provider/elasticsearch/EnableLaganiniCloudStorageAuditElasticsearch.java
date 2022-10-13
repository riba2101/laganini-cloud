package org.laganini.cloud.storage.audit.provider.elasticsearch;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration({LaganiniStorageAuditElasticsearchAutoConfiguration.class})
public @interface EnableLaganiniCloudStorageAuditElasticsearch {

}
