package org.laganini.cloud.test.performance;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith(GatlingExtension.class)
public @interface PerformanceTest {

}
