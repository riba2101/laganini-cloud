package app.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.dto.RevisionEntry;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.service.RevisionService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.elasticsearch.test.ElasticsearchContextInitializer;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@IntegrationTest(
        classes = JpaAuditTestConfiguration.class,
        initializers = {
                MariaDbContextInitializer.class,
                ElasticsearchContextInitializer.class
        }
)
class DummyRepositoryIT {

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