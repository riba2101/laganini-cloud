package org.laganini.cloud.data.entity;

public interface PurgeableEntity<T extends PurgeableReference> {

    T getPurgeable();

    void setPurgeable(T purgeableReference);

}
