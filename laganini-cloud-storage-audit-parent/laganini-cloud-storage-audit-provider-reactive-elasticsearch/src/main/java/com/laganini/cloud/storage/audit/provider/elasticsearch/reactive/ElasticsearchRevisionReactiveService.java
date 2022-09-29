package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;
import org.laganini.cloud.storage.audit.service.AbstractRevisionReactiveService;

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
