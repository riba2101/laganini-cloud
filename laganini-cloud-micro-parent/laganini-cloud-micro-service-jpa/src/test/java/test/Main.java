package test;

import org.laganini.cloud.storage.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableLaganiniCloudStorageJpa
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class).run(args);
    }

}
