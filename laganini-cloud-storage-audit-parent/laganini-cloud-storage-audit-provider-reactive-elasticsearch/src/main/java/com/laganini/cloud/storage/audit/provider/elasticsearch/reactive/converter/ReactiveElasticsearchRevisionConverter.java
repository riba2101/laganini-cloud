package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.converter;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity.ElasticsearchReactiveRevision;

public class ReactiveElasticsearchRevisionConverter
        implements RevisionConverter<Revision, ElasticsearchReactiveRevision>
{

    @Override
    public Revision to(ElasticsearchReactiveRevision target) {
        return new Revision(
                target.getId(),
                target.getType()
        );
    }

    @Override
    public ElasticsearchReactiveRevision from(Revision source) {
        return new ElasticsearchReactiveRevision(
                source.getKey(),
                source.getType()
        );
    }

}
