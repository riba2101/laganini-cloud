package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import com.laganini.cloud.storage.audit.service.AbstractRevisionReactiveService;

public class ElasticsearchRevisionReactiveService
        extends AbstractRevisionReactiveService<ElasticsearchReactiveRevision>
{

    public ElasticsearchRevisionReactiveService(
            ElasticsearchReactiveRevisionRepository repository,
            RevisionConverter<Revision, ElasticsearchReactiveRevision> converter
    )
    {
        super(repository, converter);
    }

}
