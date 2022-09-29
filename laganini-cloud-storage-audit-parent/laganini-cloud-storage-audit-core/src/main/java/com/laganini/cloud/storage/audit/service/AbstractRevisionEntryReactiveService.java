package com.laganini.cloud.storage.audit.service;

import com.laganini.cloud.logging.author.AuthorReactiveProvider;
import com.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import com.laganini.cloud.storage.audit.diff.DiffService;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import com.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;

public class AbstractRevisionEntryReactiveService<E extends RevisionEntryEntity>
        implements RevisionEntryReactiveService
{
    private static final String UNKNOWN_PREVIOUS_VALUE = "N/A";

    protected final RevisionEntryReactiveRepository<E>       repository;
    protected final RevisionEntryConverter<RevisionEntry, E> converter;
    protected final AuthorReactiveProvider                   authorReactiveProvider;
    protected final DiffService                              diffService;
    protected final Clock                                    clock;

    public AbstractRevisionEntryReactiveService(
            RevisionEntryReactiveRepository<E> repository,
            RevisionEntryConverter<RevisionEntry, E> converter,
            AuthorReactiveProvider authorReactiveProvider,
            DiffService diffService,
            Clock clock
    )
    {
        this.repository = repository;
        this.converter = converter;
        this.authorReactiveProvider = authorReactiveProvider;
        this.diffService = diffService;
        this.clock = clock;
    }

    @Override
    public Mono<RevisionEntry> getLatest(Revision revision) {
        return repository
                .findTop1ByRevisionIdOrderByInstantDesc(revision.getKey())
                .map(converter::to);
    }

    @Override
    public Flux<RevisionEntry> getAll(Revision revision) {
        return repository
                .findAllByRevisionIdOrderByInstantDesc(revision.getKey())
                .map(converter::to);
    }

    public Mono<RevisionEntry> create(Revision revision, RevisionOperation operation, Object idOrEntity) {
        switch (operation) {
            case SAVE:
                return handleSave(revision, operation, idOrEntity);
            case DELETE:
                return handleDelete(revision, operation);
        }

        return null;
    }

    private Mono<RevisionEntry> handleSave(Revision revision, RevisionOperation operation, Object idOrEntity) {
        return getLatest(revision)
                .flatMap(existing -> {
                    Map before = diffService.read(existing.getAfter());
                    Map after  = diffService.convert(idOrEntity);
                    Map diff   = diffService.calculateDiff(before, after);

                    return authorReactiveProvider
                            .provide()
                            .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                    revision.getKey(),
                                    existing.getVersion() + 1,
                                    operation,
                                    author,
                                    diffService.write(before),
                                    diffService.write(after),
                                    diffService.write(diff),
                                    LocalDateTime.now(clock)
                            ))))
                            .switchIfEmpty(repository.save(converter.from(new RevisionEntry(
                                    revision.getKey(),
                                    existing.getVersion() + 1,
                                    operation,
                                    null,
                                    diffService.write(before),
                                    diffService.write(after),
                                    diffService.write(diff),
                                    LocalDateTime.now(clock)
                            ))));
                })
                .switchIfEmpty(authorReactiveProvider
                                       .provide()
                                       .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                               revision.getKey(),
                                               0,
                                               operation,
                                               author,
                                               UNKNOWN_PREVIOUS_VALUE,
                                               diffService.write(diffService.convert(idOrEntity)),
                                               diffService.write(diffService.convert(idOrEntity)),
                                               LocalDateTime.now(clock)
                                       ))))
                                       .switchIfEmpty(repository.save(converter.from(new RevisionEntry(
                                               revision.getKey(),
                                               0,
                                               operation,
                                               null,
                                               UNKNOWN_PREVIOUS_VALUE,
                                               diffService.write(diffService.convert(idOrEntity)),
                                               diffService.write(diffService.convert(idOrEntity)),
                                               LocalDateTime.now(clock)
                                       ))))
                )
                .map(converter::to);
    }

    private Mono<RevisionEntry> handleDelete(Revision revision, RevisionOperation operation) {
        return getLatest(revision)
                .flatMap(existing -> authorReactiveProvider
                        .provide()
                        .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                revision.getKey(),
                                existing.getVersion() + 1,
                                operation,
                                author,
                                existing.getAfter(),
                                null,
                                null,
                                LocalDateTime.now(clock)
                        ))))
                )
                .switchIfEmpty(authorReactiveProvider
                                       .provide()
                                       .flatMap(author -> repository.save(converter.from(new RevisionEntry(
                                               revision.getKey(),
                                               0,
                                               operation,
                                               author,
                                               UNKNOWN_PREVIOUS_VALUE,
                                               null,
                                               null,
                                               LocalDateTime.now(clock)
                                       ))))
                )
                .map(converter::to);
    }
}
