package org.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "revision")
public class ElasticsearchReactiveRevision implements RevisionEntity {

    @Id
    private String id;
    private String type;

    public ElasticsearchReactiveRevision(String id, String type) {
        this.id = id;
        this.type = type;
    }

}
