package org.laganini.cloud.storage.audit.provider.elasticsearch;

import app.jpa.JpaAuditTestConfiguration;
import org.laganini.cloud.storage.audit.provider.AbstractRevisionServiceTest;
import org.laganini.cloud.storage.audit.provider.elasticsearch.converter.ElasticsearchRevisionConverter;
import org.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.storage.elasticsearch.test.ElasticsearchContextInitializer;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest(
        classes = JpaAuditTestConfiguration.class,
        initializers = {
                MariaDbContextInitializer.class,
                ElasticsearchContextInitializer.class
        }
)
class ElasticsearchRevisionServiceIT extends AbstractRevisionServiceTest<ElasticsearchRevision> {

    private final ElasticsearchRevisionRepository revisionRepository;

    @Autowired
    ElasticsearchRevisionServiceIT(ElasticsearchRevisionRepository revisionRepository) {
        this.revisionRepository = revisionRepository;
    }

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