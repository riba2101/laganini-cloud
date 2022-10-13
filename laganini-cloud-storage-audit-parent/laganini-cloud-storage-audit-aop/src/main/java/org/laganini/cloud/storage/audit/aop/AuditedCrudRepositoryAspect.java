package org.laganini.cloud.storage.audit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.laganini.cloud.storage.audit.aop.handler.AuditedRepositoryJoinPointHandler;

@Aspect
public class AuditedCrudRepositoryAspect {

    private final AuditedRepositoryJoinPointHandler handler;

    public AuditedCrudRepositoryAspect(AuditedRepositoryJoinPointHandler handler) {
        this.handler = handler;
    }

    @AfterReturning("execution(public * delete(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteExecuted(JoinPoint pjp) {
        handler.onDelete(pjp);
    }

    @AfterReturning("execution(public * deleteById(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteByIdExecuted(JoinPoint pjp) {
        handler.onDelete(pjp);
    }

    @AfterReturning("execution(public * deleteAll(..)) && this(org.springframework.data.repository.CrudRepository)")
    public void onDeleteAllExecuted(JoinPoint pjp) {
        handler.onDelete(pjp);
    }

    @AfterReturning(value = "execution(public * save(..)) && this(org.springframework.data.repository.CrudRepository)",
                    returning = "responseEntity")
    public void onSaveExecuted(JoinPoint pjp, Object responseEntity) {
        handler.onSave(pjp, responseEntity);
    }

    @AfterReturning(value = "execution(public * saveAll(..)) && this(org.springframework.data.repository.CrudRepository)",
                    returning = "responseEntity")
    public void onSaveAllExecuted(JoinPoint pjp, Object responseEntity) {
        handler.onSave(pjp, responseEntity);
    }

    //TODO extend to other methods
}
