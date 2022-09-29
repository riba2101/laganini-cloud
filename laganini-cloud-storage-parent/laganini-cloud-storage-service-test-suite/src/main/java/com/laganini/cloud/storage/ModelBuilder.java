package org.laganini.cloud.storage;

@FunctionalInterface
public interface ModelBuilder<MODEL> {

    MODEL build();

}
