package org.laganini.cloud.storage.audit.provider;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class AbstractReactiveRevisionServiceTest<REVISION extends RevisionEntity>
        extends AbstractRevisionServiceTestBase<REVISION>
{

    @BeforeEach
    void setUp() {
        StepVerifier
                .create(getRevisionRepository().deleteAll())
                .verifyComplete();

        StepVerifier
                .create(getRevisionRepository().saveAll(Arrays.asList(
                                givenRevision(AUDITED_ENTITY_NAME_1, 1, AUDITED_ENTITY_TYPE_1),
                                givenRevision(AUDITED_ENTITY_NAME_1, 2, AUDITED_ENTITY_TYPE_1),
                                givenRevision(AUDITED_ENTITY_NAME_2, 1, AUDITED_ENTITY_TYPE_2),
                                givenRevision(AUDITED_ENTITY_NAME_2, 2, AUDITED_ENTITY_TYPE_2)
                        ))
                )
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(4))
                .verifyComplete();
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForNameAndId() {
        int id = 1;
        StepVerifier
                .create(getRevisionService().get(AUDITED_ENTITY_NAME_1, id))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForEntity() {
        int        id   = 1;
        TestClass1 test = new TestClass1(id);

        StepVerifier
                .create(getRevisionService().get(AuditedUtils.getAudited(test).name(), test))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();
    }

    @Test
    void shouldFindOnlyRevisionForSameTypeForKey() {
        int id = 1;
        StepVerifier
                .create(getRevisionService().get(AuditedUtils.buildKey(AUDITED_ENTITY_NAME_1, id)))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();
    }

    @Test
    void shouldFindRevisionIfExists() {
        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(4))
                .verifyComplete();

        int id = 1;

        StepVerifier
                .create(getRevisionService().getOrCreate(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();

        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(4))
                .verifyComplete();
    }

    @Test
    void shouldCreateRevisionIfNotExists() {
        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(4))
                .verifyComplete();

        int id = 3;
        StepVerifier
                .create(getRevisionService().getOrCreate(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();

        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(5))
                .verifyComplete();
    }

    @Test
    void shouldCreateRevision() {
        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(4))
                .verifyComplete();

        int id = 3;
        StepVerifier
                .create(getRevisionService().create(AUDITED_ENTITY_NAME_1, AUDITED_ENTITY_TYPE_1, id))
                .assertNext(revision -> {
                    Assertions.assertThat(revision.getKey()).isEqualTo(AuditedUtils.buildKey(
                            AUDITED_ENTITY_NAME_1,
                            id
                    ));
                    Assertions.assertThat(revision.getType()).isEqualTo(AUDITED_ENTITY_TYPE_1);
                })
                .verifyComplete();

        StepVerifier
                .create(getRevisionRepository().findAll())
                .recordWith(ArrayList::new)
                .thenConsumeWhile(__ -> true)
                .consumeRecordedWith(revisions -> Assertions.assertThat(revisions).hasSize(5))
                .verifyComplete();
    }

    protected abstract RevisionReactiveRepository<REVISION> getRevisionRepository();

    protected abstract RevisionReactiveService getRevisionService();

}
