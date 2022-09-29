package org.laganini.cloud.storage.audit.utils;

import org.laganini.cloud.storage.audit.annotation.Audited;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AuditedUtilsTest {

    private static final String AUDITED_NAME = "name";

    @Test
    void shouldResolveKeyByNameAndId() {
        long id = 1;

        String key = AuditedUtils.buildKey(AUDITED_NAME, id);

        Assertions.assertThat(key).isEqualTo(AUDITED_NAME + ":" + id);
    }

    @Test
    void shouldGetAuditedAnnotation() {
        Audited audited = AuditedUtils.getAudited(new TestClass("123"));

        Assertions.assertThat(audited.name()).isEqualTo(AUDITED_NAME);
    }

    @Test
    void shouldResolveKeyByEntity() {
        TestClass testClass = new TestClass("2");

        Audited audited = AuditedUtils.getAudited(testClass);

        Assertions.assertThat(AUDITED_NAME + ":2").isEqualTo(audited.name() + ":" + testClass.getId());
    }

    @Getter
    @Setter
    @Audited(name = AUDITED_NAME)
    static class TestClass {

        private String id;

        TestClass(String id) {
            this.id = id;
        }

    }

}