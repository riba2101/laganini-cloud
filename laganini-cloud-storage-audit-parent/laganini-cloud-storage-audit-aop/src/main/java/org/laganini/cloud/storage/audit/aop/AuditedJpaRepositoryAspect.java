package org.laganini.cloud.storage.audit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;

@Aspect
public class AuditedJpaRepositoryAspect {

    private final AuditedRepositoryJoinPointHandler handler;

    public AuditedJpaRepositoryAspect(AuditedRepositoryJoinPointHandler handler) {
        this.handler = handler;
    }

    @AfterReturning(value = "execution(public * saveAndFlush(..)) && this(org.springframework.data.jpa.repository.JpaRepository)",
                    returning = "responseEntity")
    public void onSaveAndFlushExecuted(JoinPoint pjp, Object responseEntity) {
        handler.onSave(pjp, responseEntity);
    }

    @AfterReturning(value = "execution(public * deleteInBatch(..)) && this(org.springframework.data.jpa.repository.JpaRepository)")
    public void onDeleteInBatchExecuted(JoinPoint pjp) {
        handler.onDelete(pjp);
    }

    //TODO extend to other methods
}
