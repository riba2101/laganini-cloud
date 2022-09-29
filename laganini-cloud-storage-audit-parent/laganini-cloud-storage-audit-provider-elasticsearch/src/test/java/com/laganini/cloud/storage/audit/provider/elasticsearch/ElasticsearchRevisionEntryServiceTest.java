package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.provider.AbstractRevisionEntryServiceTest;
import com.laganini.cloud.storage.audit.provider.RevisionEntryRepository;
import com.laganini.cloud.storage.audit.provider.TestcontainersContextInitializer;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevisionEntry;
import com.laganini.cloud.storage.audit.service.RevisionEntryService;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import test.app.jpa.JpaAuditTestConfiguration;

import java.time.LocalDateTime;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class,
                      classes = JpaAuditTestConfiguration.class)
class ElasticsearchRevisionEntryServiceTest
        extends AbstractRevisionEntryServiceTest<ElasticsearchRevision, ElasticsearchRevisionEntry>
{

    @Autowired
    private ElasticsearchRevisionRepository      revisionRepository;
    @Autowired
    private ElasticsearchRevisionService         revisionService;
    @Autowired
    private ElasticsearchRevisionEntryRepository revisionEntryRepository;
    @Autowired
    private ElasticsearchRevisionEntryService    revisionEntryService;

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