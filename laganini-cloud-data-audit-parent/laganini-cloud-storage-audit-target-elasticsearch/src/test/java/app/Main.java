package app;

import org.laganini.cloud.observability.tracing.author.AuthorProvider;
import org.laganini.cloud.data.jpa.EnableLaganiniCloudStorageJpa;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableLaganiniCloudStorageJpa(basePackages = "app.jpa")
//TODO cloud storage elastic when available
@EnableElasticsearchRepositories(basePackages = "app.elasticsearch")
public class Main {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class).run(args);
    }

    @Bean
    AuthorProvider authorProvider() {
        return () -> "test";
    }

}
