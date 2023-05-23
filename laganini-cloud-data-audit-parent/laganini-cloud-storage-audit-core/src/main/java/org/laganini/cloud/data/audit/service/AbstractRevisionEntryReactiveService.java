package org.laganini.cloud.data.audit.service;

import org.laganini.cloud.data.audit.constants.AuditConstants;
import org.laganini.cloud.observability.tracing.author.AuthorReactiveProvider;
import org.laganini.cloud.data.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.data.audit.diff.DiffService;
import org.laganini.cloud.data.audit.dto.Revision;
import org.laganini.cloud.data.audit.dto.RevisionEntry;
import org.laganini.cloud.data.audit.dto.RevisionOperation;
import org.laganini.cloud.data.audit.entity.RevisionEntity;
import org.laganini.cloud.data.audit.entity.RevisionEntryEntity;
import org.laganini.cloud.data.audit.target.RevisionEntryReactiveRepository;
import org.laganini.cloud.storage.support.DateTimeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public class AbstractRevisionEntryReactiveService<E extends RevisionEntryEntity>
        implements RevisionEntryReactiveService<E>
{

    protected final RevisionEntryReactiveRepository<E>       repository;
    protected final RevisionEntryConverter<RevisionEntry, E> converter;
    protected final AuthorReactiveProvider                   authorReactiveProvider;
    protected final DiffService                              diffService;
    protected final DateTimeService                          dateTimeService;

    public AbstractRevisionEntryReactiveService(
            RevisionEntryReactiveRepository<E> repository,
            RevisionEntryConverter<RevisionEntry, E> converter,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService,
            DateTimeService dateTimeService
    )
    {
        this.repository = repository;
        this.converter = converter;
        this.authorReactiveProvider = authorReactiveProvider;
        this.diffService = diffService;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public Mono<RevisionEntry> getLatest(Revision revision) {
        return getLatest(revision.getKey());
    }

    private Mono<RevisionEntry> getLatest(String key) {
        return repository
                .findTop1ByRevisionIdOrderByInstantDesc(key)
                .map(converter::to);
    }

    @Override
    public Flux<RevisionEntry> getAll(Revision revision) {
        return repository
                .findAllByRevisionIdOrderByInstantDesc(revision.getKey())
                .map(converter::to);
    }

    public Mono<E> prepare(RevisionEntity revision, RevisionOperation operation, Object idOrEntity) {
        switch (operation) {
            case SAVE:
                return handleSave(revision, operation, idOrEntity);
            case DELETE:
                return handleDelete(revision, operation);
        }

        return null;
    }

    private Mono<E> handleSave(RevisionEntity revision, RevisionOperation operation, Object idOrEntity) {
        return getLatest(revision.getId())
                .flatMap(existing -> {
                    Map before = diffService.read(existing.getAfter());
                    Map after  = diffService.convert(idOrEntity);
                    Map diff   = diffService.calculateDiff(before, after);

                    return authorReactiveProvider
                            .provide()
                            .defaultIfEmpty(AuditConstants.UNKNOWN_AUTHOR)
                            .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                    revision.getId(),
                                    existing.getVersion() + 1,
                                    operation,
                                    author,
                                    diffService.write(before),
                                    diffService.write(after),
                                    diffService.write(diff),
                                    dateTimeService.now()
                            ))));
                })
                .switchIfEmpty(authorReactiveProvider
                                       .provide()
                                       .defaultIfEmpty(AuditConstants.UNKNOWN_AUTHOR)
                                       .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                               revision.getId(),
                                               0,
                                               operation,
                                               author,
                                               AuditConstants.UNKNOWN_PREVIOUS_VALUE,
                                               diffService.write(diffService.convert(idOrEntity)),
                                               diffService.write(diffService.convert(idOrEntity)),
                                               dateTimeService.now()
                                       ))))
                );
    }

    private Mono<E> handleDelete(RevisionEntity revision, RevisionOperation operation) {
        return getLatest(revision.getId())
                .flatMap(existing -> authorReactiveProvider
                        .provide()
                        .defaultIfEmpty(AuditConstants.UNKNOWN_AUTHOR)
                        .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                revision.getId(),
                                existing.getVersion() + 1,
                                operation,
                                author,
                                existing.getAfter(),
                                null,
                                null,
                                dateTimeService.now()
                        ))))
                )
                .switchIfEmpty(authorReactiveProvider
                                       .provide()
                                       .defaultIfEmpty(AuditConstants.UNKNOWN_AUTHOR)
                                       .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                               revision.getId(),
                                               0,
                                               operation,
                                               author,
                                               AuditConstants.UNKNOWN_PREVIOUS_VALUE,
                                               null,
                                               null,
                                               dateTimeService.now()
                                       ))))
                );
    }
}
