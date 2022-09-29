package com.laganini.cloud.storage.audit.provider;

import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.entity.RevisionEntity;
import com.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import com.laganini.cloud.storage.audit.service.RevisionEntryService;
import com.laganini.cloud.storage.audit.service.RevisionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Optional;

public abstract class AbstractRevisionEntryServiceTest<REVISION extends RevisionEntity, ENTRY extends RevisionEntryEntity>
        extends AbstractRevisionEntryServiceTestBase<REVISION, ENTRY>
{

    //has entries
    private Revision revision_1_1;
    //is empty
    private Revision revision_1_2;
    //has entries
    private Revision revision_2_1;
    //is empty
    private Revision revision_2_2;

    @BeforeEach
    void setUp() {
        getRevisionRepository().deleteAll();
        getRevisionEntryRepository().deleteAll();

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

        REVISION revision_entity_1_1 = getRevisionRepository().save(givenRevision(testClass1_1));
        revision_1_1 = getRevisionService().get(revision_entity_1_1.getId()).orElseThrow();
        REVISION revision_entity_1_2 = getRevisionRepository().save(givenRevision(testClass1_2));
        revision_1_2 = getRevisionService().get(revision_entity_1_2.getId()).orElseThrow();
        REVISION revision_entity_2_1 = getRevisionRepository().save(givenRevision(testClass2_1));
        revision_2_1 = getRevisionService().get(revision_entity_2_1.getId()).orElseThrow();
        REVISION revision_entity_2_2 = getRevisionRepository().save(givenRevision(testClass2_2));
        revision_2_2 = getRevisionService().get(revision_entity_2_2.getId()).orElseThrow();

        getRevisionEntryRepository().save(givenRevisionEntry(
                revision_entity_1_1,
                RevisionOperation.SAVE,
                testClass1_1
        ));
        getRevisionEntryRepository().save(givenRevisionEntry(
                revision_entity_2_1,
                RevisionOperation.SAVE,
                testClass2_1
        ));
    }

    @Test
    void shouldFindLatestRevisionEntry() {
        Optional<RevisionEntry> revision = getRevisionEntryService().getLatest(revision_2_1);

        Assertions.assertThat(revision).isPresent();
        Assertions.assertThat(revision.get().getVersion()).isEqualTo(0);
    }

    @Test
    void shouldFindAllRevisionEntries() {
        Collection<RevisionEntry> revisionEntries = getRevisionEntryService().getAll(revision_1_1);

        Assertions.assertThat(revisionEntries).hasSize(1);
        Assertions.assertThat(revisionEntries).extracting(RevisionEntry::getVersion).containsOnly(0L);
        Assertions
                .assertThat(revisionEntries)
                .extracting(RevisionEntry::getRevisionId)
                .containsOnly(revision_1_1.getKey());
    }

    @Test
    void shouldSaveSaveRevisionEntryIfNotExists() {
        TestClass1 testClass1_2 = new TestClass1(2, "test1_2-1");
        RevisionEntry revisionEntry = getRevisionEntryService().create(
                revision_1_2,
                RevisionOperation.SAVE,
                testClass1_2
        );

        Assertions.assertThat(revisionEntry.getRevisionId()).isEqualTo(revision_1_2.getKey());
        Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(0);
    }

    @Test
    void shouldSaveSaveRevisionEntryIfExists() {
        TestClass1 testClass1_1 = new TestClass1(1, "test1_1-1");
        RevisionEntry revisionEntry = getRevisionEntryService().create(
                revision_1_1,
                RevisionOperation.SAVE,
                testClass1_1
        );

        Assertions.assertThat(revisionEntry.getRevisionId()).isEqualTo(revision_1_1.getKey());
        Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(1);
    }

    @Test
    void shouldSaveDeleteRevisionEntryIfNotExists() {
        TestClass1 testClass1_2 = new TestClass1(2, "test1_2");
        RevisionEntry revisionEntry = getRevisionEntryService().create(
                revision_1_2,
                RevisionOperation.DELETE,
                testClass1_2
        );

        Assertions.assertThat(revisionEntry.getRevisionId()).isEqualTo(revision_1_2.getKey());
        Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(0);
    }

    @Test
    void shouldSaveDeleteRevisionEntryIfExists() {
        TestClass1 testClass1_1 = new TestClass1(1, "test1_1");
        RevisionEntry revisionEntry = getRevisionEntryService().create(
                revision_1_1,
                RevisionOperation.DELETE,
                testClass1_1
        );

        Assertions.assertThat(revisionEntry.getRevisionId()).isEqualTo(revision_1_1.getKey());
        Assertions.assertThat(revisionEntry.getVersion()).isEqualTo(1);
    }

    protected abstract RevisionRepository<REVISION> getRevisionRepository();

    protected abstract RevisionService getRevisionService();

    protected abstract RevisionEntryRepository<ENTRY> getRevisionEntryRepository();

    protected abstract RevisionEntryService getRevisionEntryService();

}