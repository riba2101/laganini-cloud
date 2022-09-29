package org.laganini.cloud.storage.audit.provider.jpa;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@EnableJpaRepositories
@EnableAspectJAutoProxy
@Import(LaganiniStorageAuditInitializer.class)
public @interface EnableLaganiniCloudStorageAuditJpa {

//    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackages")
//    String[] basePackages() default {"org.laganini.cloud.storage.audit.provider.jpa"};
//
//    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "entityManagerFactoryRef")
//    String entityManagerFactoryRef() default "entityManagerFactory";

}
