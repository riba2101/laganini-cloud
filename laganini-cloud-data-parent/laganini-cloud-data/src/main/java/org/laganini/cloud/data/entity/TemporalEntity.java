package org.laganini.cloud.data.entity;

public interface TemporalEntity<T extends TemporalReference> {

    T getTemporal();

    void setTemporal(T temporalReference);

}
