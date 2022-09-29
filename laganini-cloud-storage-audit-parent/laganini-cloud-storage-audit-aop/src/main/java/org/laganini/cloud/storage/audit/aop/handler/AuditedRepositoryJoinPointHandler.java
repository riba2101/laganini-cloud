package org.laganini.cloud.storage.audit.aop.handler;

import org.laganini.cloud.storage.audit.annotation.Audited;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.entity.IdentityEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.DefaultRepositoryMetadata;

@Slf4j
public class AuditedRepositoryJoinPointHandler extends AbstractAuditedJoinPointHandler {

    private final RevisionService      revisionService;
    private final RevisionEntryService revisionEntryService;

    public AuditedRepositoryJoinPointHandler(
            RevisionService revisionService,
            RevisionEntryService revisionEntryService
    )
    {
        this.revisionService = revisionService;
        this.revisionEntryService = revisionEntryService;
    }

    public void onDelete(JoinPoint pjp) {
        Audited audited = getAuditedAnnotation(pjp);
        if (audited == null) {
            //no annotation present, not audited
            log.debug("Audited annotation missing on entity, pjp: {}", pjp);
            return;
        }

        Iterable<?> arguments = getArguments(pjp);
        Class<?>    iface     = getRepositoryInterface(pjp);
        if (iface == null) {
            //could not resolve repository interface
            log.debug("Could not resolve repository interface: {}", pjp);
            return;
        }
        RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(iface);

        arguments.forEach(argument -> registerRevision(audited, metadata, RevisionOperation.DELETE, argument));
    }

    public void onSave(JoinPoint pjp, Object responseEntity) {
        Audited audited = getAuditedAnnotation(pjp);
        if (audited == null) {
            //no annotation present, not audited
            log.debug("Audited annotation missing on entity, pjp: {}", pjp);
            return;
        }

        Iterable<?> arguments = getArguments(responseEntity);
        Class<?>    iface     = getRepositoryInterface(pjp);
        if (iface == null) {
            //could not resolve repository interface
            log.debug("Could not resolve repository interface: {}", pjp);
            return;
        }
        RepositoryMetadata metadata = DefaultRepositoryMetadata.getMetadata(iface);

        arguments.forEach(argument -> registerRevision(audited, metadata, RevisionOperation.SAVE, argument));
    }

    private void registerRevision(
            Audited audited,
            RepositoryMetadata metadata,
            RevisionOperation operation,
            Object argument
    )
    {
        if (isIdClass(metadata, argument)) {
            registerRevisionById(audited, operation, metadata.getDomainType(), argument);
        } else if (isDomainClass(metadata, argument)) {
            registerRevisionByEntity(audited, operation, argument.getClass(), argument);
        } else {
            log.debug("Registered a - {}, but couldn't handle", operation);
        }
    }

    private void registerRevisionById(Audited audited, RevisionOperation operation, Class<?> entityClazz, Object id) {
        try {
            Revision revision = revisionService.getOrCreate(
                    AuditedUtils.buildName(audited, entityClazz),
                    entityClazz.getSimpleName(),
                    id
            );
            RevisionEntry revisionEntry = revisionEntryService.create(revision, operation, id);
            log.debug(
                    "Registered a - {} - of: {}; revision: {}; revision entry: {}",
                    operation,
                    id,
                    revision,
                    revisionEntry
            );
        } catch (Exception e) {
            log.debug("Could not register a - {} - revision for id: {}", operation, id, e);
        }
    }

    private void registerRevisionByEntity(
            Audited audited,
            RevisionOperation operation,
            Class<?> entityClazz,
            Object entity
    )
    {
        try {
            if (!IdentityEntity.class.isAssignableFrom(entityClazz)) {
                log.debug("Could not register a - {} - revision for entity: {}", operation, entity);
                return;
            }

            IdentityEntity<?> identityEntity = (IdentityEntity<?>) entity;
            Object            id             = identityEntity.getId();
            Revision revision = revisionService.getOrCreate(
                    AuditedUtils.buildName(audited, entityClazz),
                    entityClazz.getSimpleName(),
                    id
            );
            RevisionEntry revisionEntry = revisionEntryService.create(revision, operation, entity);
            log.debug(
                    "Registered a - {} - of: {}; revision: {}; revision entry: {}",
                    operation,
                    id,
                    revision,
                    revisionEntry
            );
        } catch (Exception e) {
            log.debug("Could not register a - {} - revision for: {}", operation, entity, e);
        }
    }

}
