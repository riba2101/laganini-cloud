package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.logging.author.AuthorProvider;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableLaganiniCloudStorageAuditJpa
public class JpaAuditTestConfiguration {

    @Bean
    AuthorProvider authorProvider() {
        return () -> "test";
    }

}
