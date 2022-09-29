package org.laganini.cloud.metric;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LaganiniPrometheusAutoConfiguration {

    private final BuildProperties buildProperties;

    public LaganiniPrometheusAutoConfiguration(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("application", buildProperties.getName());
    }

}
