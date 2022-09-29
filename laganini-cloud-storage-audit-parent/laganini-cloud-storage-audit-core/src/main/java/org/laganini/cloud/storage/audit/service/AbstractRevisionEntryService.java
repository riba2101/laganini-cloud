package org.laganini.cloud.storage.audit.service;

import org.laganini.cloud.logging.author.AuthorProvider;
import org.laganini.cloud.storage.audit.converter.RevisionEntryConverter;
import org.laganini.cloud.storage.audit.diff.DiffService;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import org.laganini.cloud.storage.audit.provider.RevisionEntryRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbstractRevisionEntryService<E extends RevisionEntryEntity> implements RevisionEntryService {
    private static final String UNKNOWN_PREVIOUS_VALUE = "N/A";

    protected final RevisionEntryRepository<E>               repository;
    protected final RevisionEntryConverter<RevisionEntry, E> converter;
    protected final AuthorProvider                           authorProvider;
    protected final DiffService                              diffService;
    protected final Clock                                    clock;

    public AbstractRevisionEntryService(
            RevisionEntryRepository<E> repository,
            RevisionEntryConverter<RevisionEntry, E> converter,
            AuthorProvider authorProvider,
            DiffService diffService,
            Clock clock
    )
    {
        this.repository = repository;
        this.converter = converter;
        this.authorProvider = authorProvider;
        this.diffService = diffService;
        this.clock = clock;
    }

    @Override
    public Optional<RevisionEntry> getLatest(Revision revision) {
        return repository
                .findTop1ByRevisionIdOrderByInstantDesc(revision.getKey())
                .map(converter::to);
    }

    @Override
    public Collection<RevisionEntry> getAll(Revision revision) {
        return repository
                .findAllByRevisionIdOrderByInstantDesc(revision.getKey())
                .stream()
                .map(converter::to)
                .collect(Collectors.toList());
    }

    @Override
    public RevisionEntry create(Revision revision, RevisionOperation operation, Object idOrEntity) {

        switch (operation) {
            case SAVE:
                return handleSave(revision, operation, idOrEntity);
            case DELETE:
                return handleDelete(revision, operation);
        }

        return null;
    }

    private RevisionEntry handleSave(Revision revision, RevisionOperation operation, Object idOrEntity) {
        E revisionEntry = getLatest(revision)
                .map(existing -> {
                    Map before = diffService.read(existing.getAfter());
                    Map after  = diffService.convert(idOrEntity);
                    Map diff   = diffService.calculateDiff(before, after);

                    return repository.save(converter.from(new RevisionEntry(
                            revision.getKey(),
                            existing.getVersion() + 1,
                            operation,
                            authorProvider.provide(),
                            diffService.write(before),
                            diffService.write(after),
                            diffService.write(diff),
                            LocalDateTime.now(clock)
                    )));
                })
                .orElseGet(() -> {
                    Map after = diffService.convert(idOrEntity);

                    return repository.save(converter.from(new RevisionEntry(
                            revision.getKey(),
                            0,
                            operation,
                            authorProvider.provide(),
                            UNKNOWN_PREVIOUS_VALUE,
                            diffService.write(after),
                            diffService.write(after),
                            LocalDateTime.now(clock)
                    )));
                });

        return converter.to(revisionEntry);
    }

    private RevisionEntry handleDelete(Revision revision, RevisionOperation operation) {
        E revisionEntry = getLatest(revision)
                .map(existing -> repository.save(converter.from(new RevisionEntry(
                        revision.getKey(),
                        existing.getVersion() + 1,
                        operation,
                        authorProvider.provide(),
                        existing.getAfter(),
                        null,
                        null,
                        LocalDateTime.now(clock)
                ))))
                .orElseGet(() -> repository.save(converter.from(new RevisionEntry(
                        revision.getKey(),
                        0,
                        operation,
                        authorProvider.provide(),
                        UNKNOWN_PREVIOUS_VALUE,
                        null,
                        null,
                        LocalDateTime.now(clock)
                ))));

        return converter.to(revisionEntry);
    }
}
