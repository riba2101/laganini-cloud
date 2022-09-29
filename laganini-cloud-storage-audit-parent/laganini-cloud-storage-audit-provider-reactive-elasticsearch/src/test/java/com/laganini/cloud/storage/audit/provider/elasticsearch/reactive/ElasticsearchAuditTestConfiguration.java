package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootConfiguration
@ImportAutoConfiguration(classes = {
        ElasticsearchDataAutoConfiguration.class,
        ReactiveElasticsearchRestClientAutoConfiguration.class,
        ReactiveElasticsearchRepositoriesAutoConfiguration.class,
})
@EnableLaganiniCloudStorageAuditElasticsearchReactive
public class ElasticsearchAuditTestConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

}
