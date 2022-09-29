package com.laganini.cloud.storage.audit.provider.elasticsearch;

import com.laganini.cloud.storage.audit.provider.AbstractRevisionServiceTest;
import com.laganini.cloud.storage.audit.provider.TestcontainersContextInitializer;
import com.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionConverter;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import test.app.jpa.JpaAuditTestConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class,
                      classes = JpaAuditTestConfiguration.class)
class ElasticsearchRevisionServiceTest extends AbstractRevisionServiceTest<ElasticsearchRevision> {

    @Autowired
    private ElasticsearchRevisionRepository revisionRepository;

    @Override
    protected ElasticsearchRevision givenRevision(String name, long id, String type) {
        return new ElasticsearchRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected ElasticsearchRevisionRepository getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected ElasticsearchRevisionService getRevisionService() {
        return new ElasticsearchRevisionService(revisionRepository, new ElasticsearchRevisionConverter());
    }

}