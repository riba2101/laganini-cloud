package org.laganini.cloud.storage.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.SetEnvironmentVariable;
import org.laganini.cloud.test.suite.UnitTest;

@UnitTest
class SnapshotSupportTest {

    private SnapshotSupport snapshotSupport = new SnapshotSupport();

    @Nested
    class WithoutSupport {

        @Test
        void shouldNotRun() {
            boolean shouldRun = snapshotSupport.shouldRun();

            Assertions.assertThat(shouldRun).isFalse();
        }

    }

    @Nested
    @SetEnvironmentVariable(key = SnapshotSupport.TEST_SUPPORT_FEATURE_DB_SNAPSHOT, value = "true")
    class WithSupport {

        @Nested
        class WithNoRun {

            @Test
            void shouldNotRun() {
                boolean shouldRun = snapshotSupport.shouldRun();

                Assertions.assertThat(shouldRun).isFalse();
            }

        }

        @Nested
        @SetEnvironmentVariable(key = SnapshotSupport.TEST_SUPPORT_FEATURE_DB_SNAPSHOT_RUN, value = "true")
        class WithRun {

            @Test
            void shouldRun() {
                boolean shouldRun = snapshotSupport.shouldRun();

                Assertions.assertThat(shouldRun).isTrue();
            }

        }

    }

}