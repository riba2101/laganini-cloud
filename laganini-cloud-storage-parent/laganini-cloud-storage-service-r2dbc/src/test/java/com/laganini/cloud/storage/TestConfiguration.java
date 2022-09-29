package org.laganini.cloud.storage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication(scanBasePackages = {"org.laganini.storage"})
class TestConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

}
