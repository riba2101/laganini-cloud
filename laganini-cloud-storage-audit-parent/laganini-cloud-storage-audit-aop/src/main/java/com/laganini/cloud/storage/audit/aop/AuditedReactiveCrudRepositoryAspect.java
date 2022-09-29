package com.laganini.cloud.storage.audit.aop;

import com.laganini.cloud.storage.audit.aop.handler.AuditedReactiveRepositoryJoinPointHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Aspect
public class AuditedReactiveCrudRepositoryAspect {

    private final AuditedReactiveRepositoryJoinPointHandler handler;

    public AuditedReactiveCrudRepositoryAspect(AuditedReactiveRepositoryJoinPointHandler handler) {
        this.handler = handler;
    }

    @Around("execution(public * delete(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public Mono<Void> onDeleteExecutedReactive(ProceedingJoinPoint pjp) throws Throwable {
        return ((Mono<Void>) pjp.proceed())
                .flatMap(anything -> handler
                        .onDelete(pjp)
                        .map(ignore -> anything)
                        .defaultIfEmpty(anything)
                        .onErrorReturn(anything)
                );
    }

    @Around("execution(public * deleteById(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public Mono<Void> onDeleteByIdExecutedReactive(ProceedingJoinPoint pjp) throws Throwable {
        return ((Mono<Void>) pjp.proceed())
                .flatMap(anything -> handler
                        .onDelete(pjp)
                        .map(ignore -> anything)
                        .defaultIfEmpty(anything)
                        .onErrorReturn(anything)
                );
    }

    @Around("execution(public * deleteAll(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public Mono<Void> onDeleteAllExecutedReactive(ProceedingJoinPoint pjp) throws Throwable {
        return ((Mono<Void>) pjp.proceed())
                .flatMap(anything -> handler
                        .onDelete(pjp)
                        .map(ignore -> anything)
                        .defaultIfEmpty(anything)
                        .onErrorReturn(anything)
                );
    }

    @Around(value = "execution(public * save(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public Mono<?> onSaveExecutedReactive(ProceedingJoinPoint pjp) throws Throwable {
        return ((Mono<?>) pjp.proceed())
                .flatMap(anything -> handler
                        .onSave(pjp)
                        .collectList()
                        .map(ignore -> anything)
                        .defaultIfEmpty(anything)
                        .onErrorReturn(anything)
                );
    }

    @Around(value = "execution(public * saveAll(..)) && this(org.springframework.data.repository.reactive.ReactiveCrudRepository)")
    public Flux<?> onSaveAllExecutedReactive(ProceedingJoinPoint pjp) throws Throwable {
        return ((Flux<?>) pjp.proceed())
                .flatMap(anything -> handler
                        .onSave(pjp)
                        .map(ignore -> anything)
                        .defaultIfEmpty(anything)
                        .onErrorReturn(anything));
    }

}
