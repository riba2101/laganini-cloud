package org.laganini.cloud.storage.audit.aop.handler;

import org.laganini.cloud.storage.aop.handler.AbstractStorageJoinPointHandler;
import org.laganini.cloud.storage.audit.annotation.Audited;
import org.aspectj.lang.JoinPoint;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class AbstractAuditedJoinPointHandler extends AbstractStorageJoinPointHandler {

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
