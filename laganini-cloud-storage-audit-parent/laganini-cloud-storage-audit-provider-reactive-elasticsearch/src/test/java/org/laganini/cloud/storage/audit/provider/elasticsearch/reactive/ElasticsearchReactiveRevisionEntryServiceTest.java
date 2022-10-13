package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.provider.AbstractReactiveRevisionEntryServiceTest;
import org.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import org.laganini.cloud.storage.audit.provider.RevisionReactiveRepository;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import org.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import org.laganini.cloud.storage.audit.service.RevisionReactiveService;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.laganini.cloud.test.testcontainer.elasticsearch.ElasticsearchContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@IntegrationTest(initializers = ElasticsearchContextInitializer.class)
class ElasticsearchReactiveRevisionEntryServiceTest
        extends AbstractReactiveRevisionEntryServiceTest<ElasticsearchReactiveRevision, ElasticsearchReactiveRevisionEntry>
{

    @Autowired
    private ElasticsearchReactiveRevisionRepository      revisionRepository;
    @Autowired
    private ElasticsearchRevisionReactiveService         revisionService;
    @Autowired
    private ElasticsearchReactiveRevisionEntryRepository revisionEntryRepository;
    @Autowired
    private ElasticsearchRevisionEntryReactiveService    revisionEntryService;

    @Override
    protected RevisionReactiveRepository<ElasticsearchReactiveRevision> getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected RevisionReactiveService getRevisionService() {
        return revisionService;
    }

    @Override
    protected RevisionEntryReactiveRepository<ElasticsearchReactiveRevisionEntry> getRevisionEntryRepository() {
        return revisionEntryRepository;
    }

    @Override
    protected RevisionEntryReactiveService getRevisionEntryService() {
        return revisionEntryService;
    }

    @Override
    protected ElasticsearchReactiveRevision givenRevision(String name, long id, String type) {
        return new ElasticsearchReactiveRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected ElasticsearchReactiveRevisionEntry givenRevisionEntry(
            ElasticsearchReactiveRevision revision,
            RevisionOperation operation,
            Object entity
    )
    {
        return new ElasticsearchReactiveRevisionEntry(
                null,
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