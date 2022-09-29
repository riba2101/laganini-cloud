package test.app;

import org.laganini.cloud.rmi.service.LaganiniCloudServerApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"test.app"})
@LaganiniCloudServerApplication(basePackages = {"test.app"})
public class TestConfiguration {

}
