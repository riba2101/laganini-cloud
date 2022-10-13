package test;

import org.laganini.cloud.storage.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.repository.TestSearchCriteriaMapper;

@EnableLaganiniCloudStorageJpa(basePackages = {"test"})
@SpringBootApplication(scanBasePackages = {"test"})
public class TestConfiguration {

    @Bean
    TestSearchCriteriaMapper testSearchCriteriaMapper() {
        return new TestSearchCriteriaMapper();
    }

}
