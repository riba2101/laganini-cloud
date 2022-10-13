package org.laganini.cloud.storage.audit.provider.elasticsearch;

import app.jpa.JpaAuditTestConfiguration;
import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.provider.AbstractRevisionEntryServiceTest;
import org.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import org.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import org.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevisionEntry;
import org.laganini.cloud.storage.audit.service.RevisionEntryService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.elasticsearch.test.ElasticsearchContextInitializer;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@IntegrationTest(
        classes = JpaAuditTestConfiguration.class,
        initializers = {
                MariaDbContextInitializer.class,
                ElasticsearchContextInitializer.class
        }
)
class ElasticsearchRevisionEntryServiceIT
        extends AbstractRevisionEntryServiceTest<ElasticsearchRevision, ElasticsearchRevisionEntry>
{

    private final ElasticsearchRevisionRepository      revisionRepository;
    private final ElasticsearchRevisionService         revisionService;
    private final ElasticsearchRevisionEntryRepository revisionEntryRepository;
    private final ElasticsearchRevisionEntryService    revisionEntryService;

    @Autowired
    ElasticsearchRevisionEntryServiceIT(
            ElasticsearchRevisionRepository revisionRepository,
            ElasticsearchRevisionService revisionService,
            ElasticsearchRevisionEntryRepository revisionEntryRepository,
            ElasticsearchRevisionEntryService revisionEntryService
    )
    {
        this.revisionRepository = revisionRepository;
        this.revisionService = revisionService;
        this.revisionEntryRepository = revisionEntryRepository;
        this.revisionEntryService = revisionEntryService;
    }

    @Override
    protected ElasticsearchRevisionRepository getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected ElasticsearchRevisionService getRevisionService() {
        return revisionService;
    }

    @Override
    protected RevisionEntryRepository<ElasticsearchRevisionEntry> getRevisionEntryRepository() {
        return revisionEntryRepository;
    }

    @Override
    protected RevisionEntryService getRevisionEntryService() {
        return revisionEntryService;
    }

    @Override
    protected ElasticsearchRevision givenRevision(String name, long id, String type) {
        return new ElasticsearchRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected ElasticsearchRevisionEntry givenRevisionEntry(
            ElasticsearchRevision revision,
            RevisionOperation operation,
            Object entity
    )
    {
        return new ElasticsearchRevisionEntry(
                null,
                0,
                revision.getId(),
                operation,
                null,
                null,
                null,
                null,
                LocalDateTime.now()
        );
    }

}