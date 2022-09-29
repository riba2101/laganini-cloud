package com.laganini.cloud.storage.audit.utils;

import com.laganini.cloud.storage.audit.annotation.Audited;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuditedUtils {

    public static Audited getAudited(Object entity) {
        Class<?> clazz = entity.getClass();
        if (clazz.getAnnotation(Audited.class) != null) {
            return clazz.getAnnotation(Audited.class);
        }

        return null;
    }

    public static String buildName(Object entity) {
        Audited audited = getAudited(entity);
        if (audited == null) {
            return null;
        }

        return buildName(audited, entity);
    }

    public static String buildName(Audited audited, Object entity) {
        if (audited == null) {
            return null;
        }

        if (!audited.name().isEmpty()) {
            return audited.name();
        }

        return entity.getClass().getSimpleName();
    }

    public static String buildKey(String name, Object id) {
        return name + ":" + id;
    }

}
