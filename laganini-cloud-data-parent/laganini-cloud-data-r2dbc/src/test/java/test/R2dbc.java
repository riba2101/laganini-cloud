package test;

import org.laganini.cloud.data.r2dbc.EnableLaganiniCloudStorageR2dbc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableLaganiniCloudStorageR2dbc
@SpringBootApplication
class R2dbc {

    public static void main(String[] args) {
        new SpringApplicationBuilder(R2dbc.class).run(args);
    }

}
