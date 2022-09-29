package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.domain.EntityScanPackages;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

class LaganiniStorageAuditInitializer implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata metadata,
            BeanDefinitionRegistry registry,
            BeanNameGenerator generator
    )
    {
        String packageName = JpaRevision.class.getPackageName();
        EntityScanPackages.register(registry, packageName);
    }

}
