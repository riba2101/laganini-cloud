package com.laganini.cloud.storage.jpa.aop;

import com.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class JpaEntityAspect {

    private final EntityJoinPointHandler handler;

    public JpaEntityAspect(EntityJoinPointHandler handler) {
        this.handler = handler;
    }

    @Before(value = "execution(public * save(..)) && this(org.springframework.data.jpa.repository.JpaRepository)")
    public void onSaveExecuted(JoinPoint pjp) {
        handler.preSave(pjp);
    }

    @Before(value = "execution(public * saveAndFlush(..)) && this(org.springframework.data.jpa.repository.JpaRepository)")
    public void onSaveAndFlushExecuted(JoinPoint pjp) {
        handler.preSave(pjp);
    }

}
