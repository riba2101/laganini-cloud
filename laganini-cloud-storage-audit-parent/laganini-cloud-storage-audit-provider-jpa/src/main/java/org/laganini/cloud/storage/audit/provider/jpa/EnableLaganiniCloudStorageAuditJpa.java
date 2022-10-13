package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.aop.LaganiniStorageAuditAopAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ImportAutoConfiguration({
        LaganiniStorageAuditAopAutoConfiguration.class,
        LaganiniStorageAuditJpaProviderAutoConfiguration.class,
        LaganiniStorageAuditJpaAutoConfiguration.class
})
public @interface EnableLaganiniCloudStorageAuditJpa {

}
