package org.laganini.cloud.storage.test;

import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.atomic.AtomicBoolean;

@Getter
class SnapshotSupport {

    public static final String DEFAULT_DATABASE_SNAPSHOT_FS_PATH        = "src/test/resources/db";
    public static final String DEFAULT_DATABASE_SNAPSHOT_FS_NAME        = "database-snapshot.sql";
    public static final String DEFAULT_DATABASE_SNAPSHOT_CONTAINER_NAME = "/database-snapshot.sql";

    public static final String TEST_SUPPORT_FEATURE_DB_SNAPSHOT     = "test.feature.db.snapshot";
    public static final String TEST_SUPPORT_FEATURE_DB_SNAPSHOT_RUN = "test.feature.db.snapshot.run";

    private final AtomicBoolean REBUILT = new AtomicBoolean(false);

    private final SnapshotSupport.Configuration configuration;

    public SnapshotSupport() {
        this(Configuration.builder().build());
    }

    public SnapshotSupport(Configuration configuration) {
        this.configuration = configuration;
    }

    @SneakyThrows
    public void setup(String path) {
        if (configuration.isEnabled()) {
            Path snapshotPath = Path.of(path);

            File file = snapshotPath.toFile();
            if (!file.exists()) {
                file.createNewFile();
            }
        }
    }

    @SneakyThrows
    public void prepare(String path) {
        if (configuration.isEnabled()) {
            Path snapshotPath = Path.of(path);

            FileChannel
                    .open(snapshotPath, StandardOpenOption.WRITE)
                    .truncate(0)
                    .close();
        }
    }

    public boolean shouldRun() {
        if (!configuration.isEnabled()) {
            return false;
        }

        boolean shouldReRunMigrations = Boolean.parseBoolean(System.getenv(TEST_SUPPORT_FEATURE_DB_SNAPSHOT_RUN));

        return shouldReRunMigrations && !REBUILT.get();
    }

    public void markRun() {
        if (configuration.isEnabled()) {
            REBUILT.set(true);
        }
    }

    public String buildFsPath() {
        return configuration.getPath() + "/" + configuration.getName();
    }

    @Builder
    @Getter
    public static class Configuration {

        @Builder.Default
        private boolean enabled = Boolean.parseBoolean(System.getenv(TEST_SUPPORT_FEATURE_DB_SNAPSHOT));
        @Builder.Default
        private String  path    = DEFAULT_DATABASE_SNAPSHOT_FS_PATH;
        @Builder.Default
        private String  name    = DEFAULT_DATABASE_SNAPSHOT_FS_NAME;

    }

}
