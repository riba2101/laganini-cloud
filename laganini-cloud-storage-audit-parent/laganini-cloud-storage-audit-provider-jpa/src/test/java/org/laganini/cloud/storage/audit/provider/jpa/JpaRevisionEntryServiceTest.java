package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.provider.AbstractRevisionEntryServiceTest;
import org.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import org.laganini.cloud.storage.audit.provider.TestcontainersContextInitializer;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevisionEntry;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

@IntegrationTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class, classes = JpaAuditTestConfiguration.class)
class JpaRevisionEntryServiceTest extends AbstractRevisionEntryServiceTest<JpaRevision, JpaRevisionEntry> {

    @Autowired
    private JpaRevisionRepository      revisionRepository;
    @Autowired
    private JpaRevisionService         revisionService;
    @Autowired
    private JpaRevisionEntryRepository revisionEntryRepository;
    @Autowired
    private JpaRevisionEntryService    revisionEntryService;

    @Override
    protected JpaRevisionRepository getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected JpaRevisionService getRevisionService() {
        return revisionService;
    }

    @Override
    protected RevisionEntryRepository<JpaRevisionEntry> getRevisionEntryRepository() {
        return revisionEntryRepository;
    }

    @Override
    protected RevisionEntryService getRevisionEntryService() {
        return revisionEntryService;
    }

    @Override
    protected JpaRevision givenRevision(String name, long id, String type) {
        return new JpaRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected JpaRevisionEntry givenRevisionEntry(
            JpaRevision revision,
            RevisionOperation operation,
            Object entity
    )
    {
        return new JpaRevisionEntry(
                AuditedUtils.buildKey(revision.getId(), 0),
                0,
                revision.getId(),
                operation,
                null,
                "{}",
                "{}",
                "{}",
                LocalDateTime.now()
        );
    }
}