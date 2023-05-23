package org.laganini.cloud.rmi.client;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.laganini.cloud.rmi.client.feign.FeignExceptionDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class LaganiniFeignClientAutoConfiguration {

//    private final ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;
//
//    @Bean
//    @ConditionalOnMissingBean
//    Encoder feignEncoder() {
//        return new SpringEncoder(messageConverters);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    Decoder feignDecoder() {
//        return new FeignReactiveDecoder(new SpringDecoder(messageConverters));
//    }

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
