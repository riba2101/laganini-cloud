package org.laganini.cloud.storage.audit.provider;

import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractRevisionServiceTest<REVISION extends RevisionEntity>
        extends AbstractRevisionServiceTestBase<REVISION>
{

    @BeforeEach
    void setUp() {
        getRevisionRepository().deleteAll();
        getRevisionRepository().saveAll(Arrays.asList(
                givenRevision(AUDITED_ENTITY_NAME_1, 1, AUDITED_ENTITY_TYPE_1),
                givenRevision(AUDITED_ENTITY_NAME_1, 2, AUDITED_ENTITY_TYPE_1),
                givenRevision(AUDITED_ENTITY_NAME_2, 1, AUDITED_ENTITY_TYPE_2),
                givenRevision(AUDITED_ENTITY_NAME_2, 2, AUDITED_ENTITY_TYPE_2)
        ));
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForNameAndId() {
        int                id       = 1;
        Optional<Revision> revision = getRevisionService().get(AUDITED_ENTITY_NAME_1, id);

        Assertions.assertThat(revision).isPresent();
        Assertions.assertThat(revision.get().getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.get().getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForEntity() {
        int                id       = 1;
        TestClass1         test     = new TestClass1(id);
        Optional<Revision> revision = getRevisionService().get(test);

        Assertions.assertThat(revision).isPresent();
        Assertions.assertThat(revision.get().getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.get().getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForKey() {
        int                id       = 1;
        Optional<Revision> revision = getRevisionService().get(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));

        Assertions.assertThat(revision).isPresent();
        Assertions.assertThat(revision.get().getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.get().getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    @Test
    void shouldFindRevisionIfExists() {
        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(4);

        int      id       = 1;
        Revision revision = getRevisionService().getOrCreate(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id);

        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(4);
        Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    @Test
    void shouldCreateRevisionIfNotExists() {
        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(4);

        int      id       = 3;
        Revision revision = getRevisionService().getOrCreate(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id);

        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(5);
        Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    @Test
    void shouldCreateRevision() {
        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(4);

        int      id       = 3;
        Revision revision = getRevisionService().create(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id);

        Assertions.assertThat(getRevisionRepository().findAll()).hasSize(5);
        Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id));
        Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
    }

    protected abstract RevisionRepository<REVISION> getRevisionRepository();

    protected abstract RevisionService getRevisionService();

}