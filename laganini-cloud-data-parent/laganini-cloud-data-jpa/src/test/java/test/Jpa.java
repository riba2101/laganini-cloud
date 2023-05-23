package test;

import org.laganini.cloud.data.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableLaganiniCloudStorageJpa
@SpringBootApplication
public class Jpa {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Jpa.class).run(args);
    }

}
