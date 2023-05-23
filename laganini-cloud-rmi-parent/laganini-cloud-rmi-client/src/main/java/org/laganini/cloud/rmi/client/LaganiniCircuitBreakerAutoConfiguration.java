package org.laganini.cloud.rmi.client;

import org.laganini.cloud.exception.BusinessException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class LaganiniCircuitBreakerAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        //TODO properties
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig
                .custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(10))
                .ignoreExceptions(BusinessException.class)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig
                .custom()
                .timeoutDuration(Duration.ofSeconds(300))
                .build();

        return factory -> factory
                .configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(timeLimiterConfig)
                        .circuitBreakerConfig(circuitBreakerConfig)
                        .build());
    }

}
