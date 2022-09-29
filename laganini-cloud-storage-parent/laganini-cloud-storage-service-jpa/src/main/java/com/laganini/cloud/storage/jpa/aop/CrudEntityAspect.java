package org.laganini.cloud.storage.jpa.aop;

import org.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class CrudEntityAspect {

    private final EntityJoinPointHandler handler;

    public CrudEntityAspect(EntityJoinPointHandler handler) {
        this.handler = handler;
    }

    @Before(value = "execution(public * save(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onSaveExecuted(JoinPoint pjp) {
        handler.preSave(pjp);
    }

    @Before(value = "execution(public * saveAll(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onSaveAllExecuted(JoinPoint pjp) {
        handler.preSave(pjp);
    }

}
