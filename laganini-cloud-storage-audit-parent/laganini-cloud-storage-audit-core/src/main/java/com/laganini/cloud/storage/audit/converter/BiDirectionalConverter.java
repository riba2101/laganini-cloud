package com.laganini.cloud.storage.audit.converter;

public interface BiDirectionalConverter<S, T> {

    S to(T target);

    T from(S source);

}
