package org.laganini.cloud.data.elasticsearch.test;

import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataElasticsearchTest
public @interface ElasticsearchTest {

    @AliasFor(annotation = ContextConfiguration.class, attribute = "classes")
    Class<?>[] classes() default {};

    @AliasFor(annotation = ContextConfiguration.class, attribute = "initializers")
    Class<? extends ApplicationContextInitializer<?>>[] initializers() default {ElasticsearchContextInitializer.class};

}
