package org.laganini.cloud.storage.audit.aop.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.laganini.cloud.storage.audit.annotation.Audited;
import org.laganini.cloud.storage.audit.aop.LaganiniStorageAuditAopAutoConfiguration;
import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import org.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class AuditedReactiveRepositoryJoinPointHandler extends AbstractAuditedJoinPointHandler {

    private final RevisionReactiveService      revisionService;
    private final RevisionEntryReactiveService revisionEntryService;

    public AuditedReactiveRepositoryJoinPointHandler(
            RevisionReactiveService revisionService,
            RevisionEntryReactiveService revisionEntryService
    )
    {
        this.revisionService = revisionService;
        this.revisionEntryService = revisionEntryService;
    }

    public Mono<Void> onDelete(JoinPoint pjp) {
        Audited audited = getAuditedAnnotation(pjp);
        if (audited == null) {
            //no annotation present, not audited
            log.debug("Audited annotation missing on entity, pjp: {}", pjp);
            return Mono.empty().then();
        }

        Iterable<?> arguments = getArguments(pjp);
        Class<?>    iface     = getRepositoryInterface(pjp);
        if (iface == null) {
            //could not resolve repository interface
            log.debug("Could not resolve repository interface: {}", pjp);
            return Mono.empty().then();
        }
        RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(iface);

        return Flux
                .fromIterable(arguments)
                .flatMap(argument -> registerRevision(audited, metadata, RevisionOperation.DELETE, argument))
                .collectList()
                .then();
    }

    public <T> Flux<T> onSave(JoinPoint pjp) {
        Audited audited = getAuditedAnnotation(pjp);
        if (audited == null) {
            //no annotation present, not audited
            log.debug("Audited annotation missing on entity, pjp: {}", pjp);
            return Flux.empty();
        }

        Iterable<T> arguments = (Iterable<T>) getArguments(pjp);
        Class<?>    iface     = getRepositoryInterface(pjp);
        if (iface == null) {
            //could not resolve repository interface
            log.debug("Could not resolve repository interface: {}", pjp);
            return Flux.empty();
        }
        RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(iface);

        return Flux
                .fromIterable(arguments)
                .flatMap(argument -> registerRevision(audited, metadata, RevisionOperation.SAVE, argument))
                .thenMany(Flux.fromIterable(arguments));
    }

    private Mono<Void> registerRevision(
            Audited audited,
            RepositoryMetadata metadata,
            RevisionOperation operation,
            Object argument
    )
    {
        if (isIdClass(metadata, argument)) {
            return registerRevisionById(audited, operation, metadata.getDomainType(), argument);
        } else if (isDomainClass(metadata, argument)) {
            return registerRevisionByEntity(audited, operation, argument.getClass(), argument);
        } else {
            log.debug("Registered a - {}, but couldn't handle", operation);
        }

        return Mono.empty().then();
    }

    private Mono<Void> registerRevisionById(
            Audited audited,
            RevisionOperation operation,
            Class<?> entityClazz,
            Object id
    )
    {
        try {
            return revisionService
                    .getOrCreate(AuditedUtils.buildName(audited, entityClazz), entityClazz.getSimpleName(), id)
                    .flatMap(revision -> revisionEntryService
                            .create(revision, operation, id)
                            .map(revisionEntry -> {
                                log.debug(
                                        "Registered a - {} - of: {}; revision: {}; revision entry: {}",
                                        operation,
                                        id,
                                        revision,
                                        revisionEntry
                                );

                                return revisionEntry;
                            })
                    )
                    .doOnError(e -> log.debug("Could not register a - {} - revision for id: {}", operation, id, e))
                    .then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    private Mono<Void> registerRevisionByEntity(
            Audited audited,
            RevisionOperation operation,
            Class<?> entityClazz,
            Object entity
    )
    {
        try {
            if (!IdentityEntity.class.isAssignableFrom(entityClazz)) {
                log.debug("Could not register a - {} - revision for entity: {}", operation, entity);
                return Mono.empty().then();
            }

            IdentityEntity<?> identityEntity = (IdentityEntity<?>) entity;
            Object            id             = identityEntity.getId();

            return revisionService
                    .getOrCreate(AuditedUtils.buildName(audited, entityClazz), entityClazz.getSimpleName(), id)
                    .flatMap(revision -> revisionEntryService
                            .create(revision, operation, entity)
                            .map(revisionEntry -> {
                                log.debug(
                                        "Registered a - {} - of: {}; revision: {}; revision entry: {}",
                                        operation,
                                        id,
                                        revision,
                                        revisionEntry
                                );

                                return revisionEntry;
                            })
                    )
                    .doOnError(e -> log.debug("Could not register a - {} - revision for: {}", operation, entity, e))
                    .then();
        } catch (Exception e) {
            return Mono.error(e);
        }
    }


}
