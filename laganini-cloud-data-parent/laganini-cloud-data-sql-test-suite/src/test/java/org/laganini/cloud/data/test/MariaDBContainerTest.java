package org.laganini.cloud.data.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.laganini.cloud.test.unit.UnitTest;

import java.io.File;

@UnitTest
class MariaDBContainerTest {

    private final MariaDBContainer container = new MariaDBContainer();

    @BeforeEach
    void setUp() {
        try {
            File file = new File(container.targetPath());
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            //ignore
        }
    }

    @Nested
    class WithoutSupport {

        @Test
        void shouldNotRun() {
            String path = container.targetPath();

            Assertions.assertThat(new File(path).exists()).isFalse();
        }

    }

    @Nested
    @SetEnvironmentVariable(key = SnapshotSupport.TEST_SUPPORT_FEATURE_DB_SNAPSHOT, value = "true")
    class WithSupport {

        @Nested
        class WithNoRun {

            @Test
            void shouldNotRun() {
                MariaDBContainer container = new MariaDBContainer();
                String           path      = container.targetPath();

                Assertions.assertThat(new File(path).exists()).isTrue();
            }

        }

        @Nested
        @SetEnvironmentVariable(key = SnapshotSupport.TEST_SUPPORT_FEATURE_DB_SNAPSHOT_RUN, value = "true")
        class WithRun {

            @Test
            void shouldRun() {
                MariaDBContainer container = new MariaDBContainer();
                String           path      = container.targetPath();

                Assertions.assertThat(new File(path).exists()).isTrue();
            }

        }

    }

}