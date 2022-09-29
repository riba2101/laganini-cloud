package org.laganini.cloud.storage.r2dbc.aop;

import org.laganini.cloud.storage.aop.handler.EntityJoinPointHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ReactiveEntityAspect {

    private final EntityJoinPointHandler handler;

    public ReactiveEntityAspect(EntityJoinPointHandler handler) {
        this.handler = handler;
    }

    @Before(value = "execution(public * save(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public void onSaveExecutedReactive(JoinPoint pjp) {
        handler.preSave(pjp);
    }

    @Before(value = "execution(public * saveAll(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public void onSaveAllExecutedReactive(JoinPoint pjp) {
        handler.preSave(pjp);
    }

}
