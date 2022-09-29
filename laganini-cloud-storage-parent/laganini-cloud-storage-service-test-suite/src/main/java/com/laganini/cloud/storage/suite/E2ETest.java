package com.laganini.cloud.storage.suite;

import com.laganini.cloud.test.suite.IntegrationTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(DatabaseMigrationExtension.class)
@ContextConfiguration(initializers = DatasourceContextInitializer.class)
@IntegrationTest
public @interface E2ETest {

    boolean clean() default true;

    boolean migrate() default true;

}
