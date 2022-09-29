package com.laganini.cloud.storage.audit.provider.elasticsearch.converter;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.elasticsearch.entity.ElasticsearchRevision;

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
