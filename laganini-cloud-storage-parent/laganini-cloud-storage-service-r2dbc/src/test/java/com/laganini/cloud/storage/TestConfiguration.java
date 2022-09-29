package com.laganini.cloud.storage;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Clock;

@SpringBootApplication(scanBasePackages = {"com.laganini.storage"})
class TestConfiguration {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }

}
