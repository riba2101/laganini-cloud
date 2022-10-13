package org.laganini.cloud.storage.test;

public interface SqlSnapshotContainer {

    boolean snapshots();

    String targetPath();

    void setup();

    void prepare();

    void snapshot();

    void restore();

}
