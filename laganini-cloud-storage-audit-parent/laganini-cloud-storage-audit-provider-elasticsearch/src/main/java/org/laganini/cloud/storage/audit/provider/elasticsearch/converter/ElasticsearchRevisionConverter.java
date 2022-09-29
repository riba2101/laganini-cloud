package org.laganini.cloud.storage.audit.provider.elasticsearch.converter;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;

public class ElasticsearchRevisionConverter implements RevisionConverter<Revision, ElasticsearchRevision> {

    @Override
    public Revision to(ElasticsearchRevision target) {
        return new Revision(
                target.getId(),
                target.getType()
        );
    }

    @Override
    public ElasticsearchRevision from(Revision source) {
        return new ElasticsearchRevision(
                source.getKey(),
                source.getType()
        );
    }

}
