package test.app.jpa;

import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.provider.TestcontainersContextInitializer;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Collection;
import java.util.Optional;

@IntegrationTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class,
                      classes = JpaAuditTestConfiguration.class)
class DummyRepositoryTest {

    @Autowired
    private DummyRepository      dummyRepository;
    @Autowired
    private RevisionService      revisionService;
    @Autowired
    private RevisionEntryService revisionEntryService;

    @Test
    void shouldAudit() {
        Dummy                     test;
        Optional<Revision>        revision;
        Collection<RevisionEntry> revisionEntries;

        test = dummyRepository.save(new Dummy("test"));
        Assertions.assertThat(test.getId()).isNotNull();
        revision = revisionService.get(AuditedUtils.getAudited(test).name(), test.getId());
        Assertions.assertThat(revision).isPresent();
        revisionEntries = revisionEntryService.getAll(revision.get());
        Assertions.assertThat(revisionEntries).isNotEmpty();

        test.setName("other");
        test = dummyRepository.save(test);
        Assertions.assertThat(test.getId()).isNotNull();
        revision = revisionService.get(AuditedUtils.getAudited(test).name(), test.getId());
        Assertions.assertThat(revision).isPresent();
        revisionEntries = revisionEntryService.getAll(revision.get());
        Assertions.assertThat(revisionEntries).isNotEmpty();

        dummyRepository.delete(test);
        revision = revisionService.get(AuditedUtils.getAudited(test).name(), test.getId());
        Assertions.assertThat(revision).isPresent();
        revisionEntries = revisionEntryService.getAll(revision.get());
        Assertions.assertThat(revisionEntries).isNotEmpty();
    }

}