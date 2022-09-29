package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.storage.audit.provider.AbstractReactiveRevisionServiceTest;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter.ReactiveElasticsearchRevisionConverter;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import com.laganini.cloud.storage.audit.utils.AuditedUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class)
public class ElasticsearchReactiveRevisionServiceTest
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