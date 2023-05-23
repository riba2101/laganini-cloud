package org.laganini.cloud.storage.r2dbc;

import org.springframework.boot.autoconfigure.flyway.FlywayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(FlywayProperties.class)
public class LaganiniStorageServiceR2dbcAutoConfiguration {

}
