package com.laganini.cloud.storage.audit.provider;

import com.laganini.cloud.storage.audit.annotation.Audited;
import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.entity.RevisionEntity;
import com.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public abstract class AbstractRevisionEntryServiceTestBase<REVISION extends RevisionEntity, ENTRY extends RevisionEntryEntity> {

    protected static final String AUDITED_ENTITY_NAME_1 = "test_entry_1";
    protected static final String AUDITED_ENTITY_TYPE_1 = TestClass1.class.getSimpleName();
    protected static final String AUDITED_ENTITY_NAME_2 = "test_entry_2";
    protected static final String AUDITED_ENTITY_TYPE_2 = TestClass1.class.getSimpleName();

    protected REVISION givenRevision(TestClass1 arg) {
        Audited annotation = AuditedUtils.getAudited(arg);

        return givenRevision(annotation.name(), arg.getId(), arg.getClass().getSimpleName());
    }

    protected REVISION givenRevision(TestClass2 arg) {
        Audited annotation = AuditedUtils.getAudited(arg);

        return givenRevision(annotation.name(), arg.getId(), arg.getClass().getSimpleName());
    }

    protected abstract REVISION givenRevision(String name, long id, String type);

    protected abstract ENTRY givenRevisionEntry(REVISION revision, RevisionOperation operation, Object entity);

    @Getter
    @Setter
    @Audited(name = AUDITED_ENTITY_NAME_1)
    public static class TestClass1 {

        private Integer       id;
        private String        name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TestClass1(Integer id, String name) {
            this.id = id;
            this.name = name;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
    }

    @Getter
    @Setter
    @Audited(name = AUDITED_ENTITY_NAME_2)
    public static class TestClass2 {

        private Integer       id;
        private String        name;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TestClass2(Integer id, String name) {
            this.id = id;
            this.name = name;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }

    }

}