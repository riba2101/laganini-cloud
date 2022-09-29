package test;

import com.laganini.cloud.storage.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableLaganiniCloudStorageJpa(basePackages = {"test"})
@SpringBootApplication(scanBasePackages = {"test"})
public class TestConfiguration {

}
