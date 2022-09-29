package org.laganini.cloud.storage;

import org.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.time.Clock;

@Configuration(proxyBeanMethods = false)
public class LaganiniStorageServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    @ConditionalOnMissingBean
    EntityJoinPointHandler entityJoinPointHandler(Clock clock) {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        Jsr310Converters.getConvertersToRegister().forEach(conversionService::addConverter);

        return new EntityJoinPointHandler(clock, conversionService);
    }

}
