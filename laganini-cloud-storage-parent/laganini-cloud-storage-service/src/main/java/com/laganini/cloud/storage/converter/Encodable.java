package com.laganini.cloud.storage.converter;

import com.laganini.cloud.common.DynamicEnum;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;

public interface Encodable<ID> {

    static <E extends Enum<E> & Encodable<?>> E enumFromString(Class<E> clazz, String name) {
        return Arrays
                .stream(clazz.getEnumConstants())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown name '" + name + "' for enum " + clazz.getName()));
    }

    static <E extends Enum<E> & Encodable<?>> E enumFromId(Class<E> clazz, Object id) {
        return Arrays
                .stream(clazz.getEnumConstants())
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown id '" + id + "' for enum " + clazz.getName()));
    }

    static <E extends DynamicEnum<E> & Encodable<?>> E dynamicEnumFromId(Class<E> clazz, Object id) {
        return Arrays
                .stream(clazz.getFields())
                .filter(ReflectionUtils::isPublicStaticFinal)
                .filter(field -> {
                    Encodable value = (Encodable) ReflectionUtils.getField(field, null);
                    if (value == null) {
                        return false;
                    }
                    return value.getId().equals(id);
                })
                .findFirst()
                .map(field -> (E) ReflectionUtils.getField(field, null))
                .orElseThrow(() -> new IllegalArgumentException("Unknown id '" + id + "' for dynamic enum " + clazz.getName()));
    }

    ID getId();

}
