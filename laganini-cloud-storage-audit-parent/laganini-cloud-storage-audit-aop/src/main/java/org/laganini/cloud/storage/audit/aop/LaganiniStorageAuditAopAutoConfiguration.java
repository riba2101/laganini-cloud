package org.laganini.cloud.storage.audit.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@EnableAspectJAutoProxy
@AutoConfiguration
public class LaganiniStorageAuditAopAutoConfiguration {

    public static final String ASYNC_EXECUTOR_BEAN = "laganiniStorageAuditAopAsync";

    @Bean
    @ConfigurationProperties(prefix = "laganini.storage.audit.async")
    LaganiniStorageAuditAsyncProperties laganiniStorageAuditAsyncProperties() {
        return new LaganiniStorageAuditAsyncProperties();
    }

    @Bean(name = ASYNC_EXECUTOR_BEAN)
    Executor laganiniStorageAuditAopAsync(LaganiniStorageAuditAsyncProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(properties.getThreadPoolCount());
        executor.setMaxPoolSize(properties.getThreadPoolCount());
        executor.setQueueCapacity(properties.getThreadPoolQueueSize());
        executor.setThreadNamePrefix("Laganini-Audit-Async-");
        executor.setRejectedExecutionHandler(new NonThrowingRejectedExecutionHandler());
        executor.initialize();

        return executor;
    }

    @Slf4j
    private static class NonThrowingRejectedExecutionHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(final Runnable runnable, final ThreadPoolExecutor executor) {
            log.error(
                    "ACS Executor rejected the tasks, Scheduled count: {}, max: {}",
                    executor.getTaskCount(),
                    executor.getMaximumPoolSize()
            );
        }

    }

}
