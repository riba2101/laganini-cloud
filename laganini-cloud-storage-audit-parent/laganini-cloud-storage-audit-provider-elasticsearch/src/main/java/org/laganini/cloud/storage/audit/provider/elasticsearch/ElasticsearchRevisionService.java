package org.laganini.cloud.storage.audit.provider.elasticsearch;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;
import org.laganini.cloud.storage.audit.service.AbstractRevisionService;

public class ElasticsearchRevisionService extends AbstractRevisionService<ElasticsearchRevision> {

    public ElasticsearchRevisionService(
            ElasticsearchRevisionRepository repository,
            RevisionConverter<Revision, ElasticsearchRevision> converter
    )
    {
        super(repository, converter);
    }

}
