package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.provider.AbstractReactiveRevisionEntryServiceTest;
import com.laganini.cloud.storage.audit.provider.RevisionEntryReactiveRepository;
import com.laganini.cloud.storage.audit.provider.RevisionReactiveRepository;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevisionEntry;
import com.laganini.cloud.storage.audit.service.RevisionEntryReactiveService;
import com.laganini.cloud.storage.audit.service.RevisionReactiveService;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDateTime;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class)
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