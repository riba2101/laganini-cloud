package org.laganini.cloud.data.test;

@FunctionalInterface
public interface ModelBuilder<MODEL> {

    MODEL build();

}
