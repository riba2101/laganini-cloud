package org.laganini.cloud.rmi.service;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AliasFor;
import org.springframework.web.reactive.config.EnableWebFlux;
import reactivefeign.spring.config.EnableReactiveFeignClients;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableWebFlux
@EnableReactiveFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public @interface LaganiniCloudServerApplication {

    @AliasFor(annotation = EnableReactiveFeignClients.class, attribute = "basePackages")
    String[] basePackages() default {};

    @AliasFor(annotation = ComponentScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

}
