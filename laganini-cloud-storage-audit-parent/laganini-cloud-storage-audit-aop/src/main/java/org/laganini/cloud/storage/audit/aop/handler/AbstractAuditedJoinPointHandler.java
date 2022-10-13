package org.laganini.cloud.storage.audit.aop.handler;

import org.aspectj.lang.JoinPoint;
import org.laganini.cloud.storage.audit.annotation.Audited;
import org.springframework.data.repository.core.RepositoryMetadata;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class AbstractAuditedJoinPointHandler {

    protected Class<?> getRepositoryInterface(JoinPoint pjp) {
        return Arrays
                .stream(pjp.getTarget().getClass().getInterfaces())
                .findFirst()
                .orElse(null);
    }

    protected Collection<?> getArguments(JoinPoint pjp) {
        List<Object> result = new ArrayList<>();

        for (Object arg : pjp.getArgs()) {
            if (arg instanceof Collection) {
                result.addAll((Collection) arg);
            } else {
                result.add(arg);
            }
        }

        return result;
    }

    protected Iterable<?> getArguments(Object args) {
        if (args instanceof Iterable) {
            return (Iterable) args;
        }
        return Collections.singletonList(args);
    }

    protected boolean isDomainClass(RepositoryMetadata metadata, Object o) {
        return metadata.getDomainType().isAssignableFrom(o.getClass());
    }

    protected boolean isIdClass(RepositoryMetadata metadata, Object o) {
        return metadata.getIdType().isAssignableFrom(o.getClass());
    }

    protected Audited getAuditedAnnotation(JoinPoint pjp) {
        for (Class<?> clazz : pjp.getTarget().getClass().getInterfaces()) {
            for (Type type : clazz.getGenericInterfaces()) {
                if (!ParameterizedType.class.isAssignableFrom(type.getClass())) {
                    continue;
                }

                ParameterizedType pt = (ParameterizedType) type;
                for (Type generic : pt.getActualTypeArguments()) {
                    Class entity = (Class) generic;
                    if (entity.getAnnotation(Audited.class) != null) {
                        return (Audited) entity.getAnnotation(Audited.class);
                    }
                }
            }
        }

        return null;
    }

}
