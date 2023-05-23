package org.laganini.cloud.rmi.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;

public class FeignClientBuilder {

    public static <T> T build(ApplicationContext applicationContext, Class<T> clazz, String baseUrl) {
        FeignClient annotation = clazz.getAnnotation(FeignClient.class);

        return new org.springframework.cloud.openfeign.FeignClientBuilder(applicationContext)
                .forType(clazz, clazz.getSimpleName())
                .url(baseUrl + annotation.path())
                .build();
    }

}
