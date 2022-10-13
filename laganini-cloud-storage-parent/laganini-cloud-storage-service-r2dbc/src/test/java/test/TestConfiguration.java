package test;

import org.laganini.cloud.storage.r2dbc.EnableLaganiniCloudStorageR2dbc;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableLaganiniCloudStorageR2dbc(basePackages = {"test"})
@SpringBootApplication(scanBasePackages = {"test"})
class TestConfiguration {

}
