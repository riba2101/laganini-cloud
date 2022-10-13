package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.storage.audit.provider.AbstractReactiveRevisionServiceTest;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter.ReactiveElasticsearchRevisionConverter;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import org.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.laganini.cloud.test.suite.IntegrationTest;
import org.laganini.cloud.test.testcontainer.elasticsearch.ElasticsearchContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest(initializers = ElasticsearchContextInitializer.class)
public class ElasticsearchReactiveRevisionServiceIT
        extends AbstractReactiveRevisionServiceTest<ElasticsearchReactiveRevision>
{

    @Autowired
    private ElasticsearchReactiveRevisionRepository revisionRepository;

    @Override
    protected ElasticsearchReactiveRevision givenRevision(String name, long id, String type) {
        return new ElasticsearchReactiveRevision(AuditedUtils.buildKey(name, id), type);
    }

    @Override
    protected ElasticsearchReactiveRevisionRepository getRevisionRepository() {
        return revisionRepository;
    }

    @Override
    protected ElasticsearchRevisionReactiveService getRevisionService() {
        return new ElasticsearchRevisionReactiveService(
                revisionRepository,
                new ReactiveElasticsearchRevisionConverter()
        );
    }

}