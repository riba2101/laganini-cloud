package org.laganini.cloud.storage.audit.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import org.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import org.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import reactor.test.StepVerifier;

import java.util.ArrayList;

public abstract class AbstractReactiveRevisionEntryServiceTest<REVISION extends RevisionEntity, ENTRY extends RevisionEntryEntity>
        extends AbstractRevisionEntryServiceTestBase<REVISION, ENTRY>
{

    @BeforeEach
    void setUp() {
        StepVerifier
                .create(getRevisionRepository().deleteAll())
                .verifyComplete();
        StepVerifier
                .create(getRevisionEntryRepository().deleteAll())
                .verifyComplete();

        AbstractRevisionEntryServiceTestBase.TestClass1 testClass1_1 = new AbstractRevisionEntryServiceTestBase.TestClass1(
                1,
                "test1_1"
        );
        AbstractRevisionEntryServiceTestBase.TestClass1 testClass1_2 = new AbstractRevisionEntryServiceTestBase.TestClass1(
                2,
                "test1_2"
        );
        AbstractRevisionEntryServiceTestBase.TestClass2 testClass2_1 = new AbstractRevisionEntryServiceTestBase.TestClass2(
                1,
                "test2_1"
        );
        AbstractRevisionEntryServiceTestBase.TestClass2 testClass2_2 = new AbstractRevisionEntryServiceTestBase.TestClass2(
                2,
                "test2_2"
        );

        StepVerifier
                .create(getRevisionRepository().save(givenRevision(testClass1_1)))
                .assertNext(revision -> Assertions
                        .assertThat(revision.getId())
                        .isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, 1))
                )
                .verifyComplete();
        StepVerifier
                .create(getRevisionRepository().save(givenRevision(testClass1_2)))
                .assertNext(revision -> Assertions
                        .assertThat(revision.getId())
                        .isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, 2))
                )
                .verifyComplete();
        StepVerifier
                .create(getRevisionRepository().save(givenRevision(testClass2_1)))
                .assertNext(revision -> Assertions
                        .assertThat(revision.getId())
                        .isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_2, 1))
                )
                .verifyComplete();
        StepVerifier
                .create(getRevisionRepository().save(givenRevision(testClass2_2)))
                .assertNext(revision -> Assertions
                        .assertThat(revision.getId())
                        .isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_2, 2))
                )
                .verifyComplete();

        StepVerifier
                .create(getRevisionEntryRepository().save(givenRevisionEntry(
                        givenRevision(testClass1_1),
                        RevisionOperation.SAVE,
                        testClass1_1
                )))
                .assertNext(revisionEntry -> Assertions
                        .assertThat(revisionEntry.getVersion())
                        .isEqualTo(0)
                )
                .verifyComplete();
        StepVerifier
                .create(getRevisionEntryRepository().save(givenRevisionEntry(
                        givenRevision(testClass2_1),
                        RevisionOperation.SAVE,
                        testClass2_1
                )))
                .assertNext(revisionEntry -> Assertions
                        .assertThat(revisionEntry.getVersion())
                        .isEqualTo(0)
                )
                .verifyComplete();
    }

    @Test
    void shouldFindLatestRevisionEntry() {
        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 1)
                                .flatMap(revision -> getRevisionEntryService().getLatest(revision))
                )
                .assertNext(revisionEntry -> Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(0))
                .verifyComplete();
    }

    @Test
    void shouldFindAllRevisionEntries() {
        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 1)
                                .flatMapMany(revision -> getRevisionEntryService().getAll(revision))
                )
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisionEntries -> {
                    Assertions.assertThat(revisionEntries).hasSize(1);
                    Assertions.assertThat(revisionEntries).extracting(RevisionEntry::getVersion).containsOnly(0L);
                    Assertions
                            .assertThat(revisionEntries)
                            .extracting(RevisionEntry::getRevisionId)
                            .containsOnly(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, 1));
                })
                .verifyComplete();
    }

    @Test
    void shouldSaveSaveRevisionEntryIfNotExists() {
        TestClass1 testClass = new TestClass1(2, "test-new");

        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 2)
                                .flatMap(revision -> getRevisionEntryService().create(
                                        revision,
                                        RevisionOperation.SAVE,
                                        testClass
                                ))
                )
                .assertNext(revisionEntry -> Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(0))
                .verifyComplete();
    }

    @Test
    void shouldSaveSaveRevisionEntryIfExists() {
        TestClass1 testClass = new TestClass1(1, "test-new");

        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 1)
                                .flatMap(revision -> getRevisionEntryService().create(
                                        revision,
                                        RevisionOperation.SAVE,
                                        testClass
                                ))
                )
                .assertNext(revisionEntry -> Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(1))
                .verifyComplete();
    }

    @Test
    @Disabled("not supported as of now")
    void shouldSaveDeleteRevisionEntryIfNotExists() {
        TestClass1 testClass = new TestClass1(2, "test-new");

        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 2)
                                .flatMap(revision -> getRevisionEntryService().create(
                                        revision,
                                        RevisionOperation.DELETE,
                                        testClass
                                ))
                )
                .assertNext(revisionEntry -> Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(0))
                .verifyComplete();
    }

    @Test
    @Disabled("not supported as of now")
    void shouldSaveDeleteRevisionEntryIfExists() {
        TestClass1 testClass = new TestClass1(1, "test-new");

        StepVerifier
                .create(getRevisionService()
                                .get(AUDITED_ENTITY_NAME_1, 1)
                                .flatMap(revision -> getRevisionEntryService().create(
                                        revision,
                                        RevisionOperation.DELETE,
                                        testClass
                                ))
                )
                .assertNext(revisionEntry -> Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(1))
                .verifyComplete();
    }

    protected abstract RevisionReactiveRepository<REVISION> getRevisionRepository();

    protected abstract RevisionReactiveService getRevisionService();

    protected abstract RevisionEntryReactiveRepository<ENTRY> getRevisionEntryRepository();

    protected abstract RevisionEntryReactiveService getRevisionEntryService();


}