package app.elasticsearch;

import app.Main;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.audit.dto.Revision;
import org.laganini.cloud.data.audit.dto.RevisionEntry;
import org.laganini.cloud.data.audit.service.RevisionEntryService;
import org.laganini.cloud.data.audit.service.RevisionService;
import org.laganini.cloud.data.audit.target.elasticsearch.AuditElasticsearchContextInitializer;
import org.laganini.cloud.data.audit.utils.AuditedUtils;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.laganini.cloud.test.integration.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

@IntegrationTest(
        application = Main.class,
        initializers = {
                MariaDbContextInitializer.class,
                AuditElasticsearchContextInitializer.class
        }
)
@ExtendWith(FlywayExtension.class)
class DummyElasticsearchRepositoryIT {

    @Autowired
    private DummyElasticsearchRepository dummyRepository;
    @Autowired
    private RevisionService              revisionService;
    @Autowired
    private RevisionEntryService         revisionEntryService;

    @Test
    void shouldAudit() {
        DummyElasticsearch test;
        Optional<Revision> revision;
        Collection<RevisionEntry> revisionEntries;

        test = dummyRepository.save(new DummyElasticsearch("test"));
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