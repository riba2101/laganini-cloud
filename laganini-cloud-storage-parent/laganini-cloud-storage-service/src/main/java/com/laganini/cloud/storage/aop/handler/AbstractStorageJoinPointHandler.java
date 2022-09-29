package org.laganini.cloud.storage.aop.handler;

import org.aspectj.lang.JoinPoint;
import org.springframework.data.repository.core.RepositoryMetadata;

import java.util.*;

public abstract class AbstractStorageJoinPointHandler {

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

}
