package org.laganini.cloud.rmi.client;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LaganiniFeignClientAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    ErrorDecoder errorDecoder() {
        return new FeignExceptionDecoder();
    }

    @Bean
    @ConditionalOnMissingBean
    Logger.Level feignLogger() {
        return Logger.Level.FULL;
    }

}
