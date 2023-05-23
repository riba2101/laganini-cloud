package org.laganini.cloud.data.test;

public interface SqlSnapshotContainer {

    String targetPath();

    void setup();

    void prepare();

    void snapshot();

    void restore();

}
