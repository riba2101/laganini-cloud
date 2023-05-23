package org.laganini.cloud.data.test;

import lombok.NonNull;
import lombok.SneakyThrows;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitStrategy;

import java.nio.file.Path;

public class MariaDBContainer
        extends org.testcontainers.containers.MariaDBContainer<MariaDBContainer>
        implements SqlSnapshotContainer
{

    public static final String IMAGE_VERSION = "mariadb:10.5.3";

    private final SnapshotSupport snapshotSupport;

    public MariaDBContainer() {
        this(IMAGE_VERSION);
    }

    public MariaDBContainer(String dockerImageName) {
        this(dockerImageName, SnapshotSupport.Configuration.builder().build());
    }

    public MariaDBContainer(SnapshotSupport.Configuration snapshotSupportConfiguration) {
        this(IMAGE_VERSION, snapshotSupportConfiguration);
    }

    public MariaDBContainer(String dockerImageName, SnapshotSupport.Configuration snapshotSupportConfiguration) {
        super(dockerImageName);

        this.snapshotSupport = new SnapshotSupport(snapshotSupportConfiguration);

        withConfigurationOverride("sql");
        setWaitStrategy(Wait.forListeningPort());

        if (snapshotSupport.getConfiguration().isEnabled()) {
            setup();

            if (snapshotSupport.shouldRun()) {
                prepare();
            }

            withFileSystemBind(
                    Path.of(targetPath()).toAbsolutePath().toString(),
                    SnapshotSupport.DEFAULT_DATABASE_SNAPSHOT_CONTAINER_NAME
            );
        }
    }

    public MariaDBContainer whitFixatedPort(int from, int to) {
        addFixedExposedPort(from, to);
        return this;
    }

    @Override
    public MariaDBContainer waitingFor(@NonNull WaitStrategy waitStrategy) {
        setWaitStrategy(waitStrategy);

        return this;
    }

    @Override
    public String targetPath() {
        return snapshotSupport.buildFsPath();
    }

    @Override
    public void setup() {
        snapshotSupport.setup(targetPath());
    }

    @Override
    public void prepare() {
        snapshotSupport.prepare(targetPath());
    }

    @SneakyThrows
    @Override
    public void snapshot() {
        if (snapshotSupport.shouldRun()) {
            execInContainer(
                    "sh",
                    "-c",
                    "mysqldump --user " + this.getUsername() + " -p" + this.getPassword() + " " + this.getDatabaseName() + " > " + SnapshotSupport.DEFAULT_DATABASE_SNAPSHOT_CONTAINER_NAME
            );
            snapshotSupport.markRun();
        }
    }

    @SneakyThrows
    @Override
    public void restore() {
        if (snapshotSupport.getConfiguration().isEnabled()) {
            execInContainer(
                    "sh",
                    "-c",
                    "mysql --user " + this.getUsername() + " -p" + this.getPassword() + " " + this.getDatabaseName() + " < " + SnapshotSupport.DEFAULT_DATABASE_SNAPSHOT_CONTAINER_NAME
            );
        }
    }

}
