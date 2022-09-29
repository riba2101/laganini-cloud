package com.laganini.cloud.storage;

@FunctionalInterface
public interface ModelBuilder<MODEL> {

    MODEL build();

}
