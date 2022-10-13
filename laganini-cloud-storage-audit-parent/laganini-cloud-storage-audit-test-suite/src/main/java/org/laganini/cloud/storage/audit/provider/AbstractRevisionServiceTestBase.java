package org.laganini.cloud.storage.audit.provider;

import lombok.Getter;
import lombok.Setter;
import org.laganini.cloud.storage.audit.annotation.Audited;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.entity.IdentityEntity;

import java.time.LocalDateTime;

abstract class AbstractRevisionServiceTestBase<REVISION extends RevisionEntity> {

    protected static final String AUDITED_ENTITY_NAME_1 = "test_1";
    protected static final String AUDITED_ENTITY_TYPE_1 = TestClass1.class.getSimpleName();
    protected static final String AUDITED_ENTITY_NAME_2 = "test_2";
    protected static final String AUDITED_ENTITY_TYPE_2 = TestClass1.class.getSimpleName();

    protected abstract REVISION givenRevision(String name, long id, String type);

    @Getter
    @Setter
    @Audited(name = AUDITED_ENTITY_NAME_1)
    public static class TestClass1 implements IdentityEntity<Integer> {

        private Integer       id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TestClass1(Integer id) {
            this.id = id;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
    }

    @Getter
    @Setter
    @Audited(name = AUDITED_ENTITY_NAME_2)
    public static class TestClass2 implements IdentityEntity<Integer> {

        private Integer       id;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public TestClass2(Integer id) {
            this.id = id;
            this.createdAt = LocalDateTime.now();
            this.updatedAt = LocalDateTime.now();
        }
    }

}