package org.laganini.cloud.storage.audit.aop;

import org.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

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

    @AfterReturning(
            "execution(public * deleteInBatch(..)) && this(org.springframework.data.jpa.repository.JpaRepository)")
    public void onDeleteInBatchExecuted(JoinPoint pjp) {
        handler.onDelete(pjp);
    }

}
