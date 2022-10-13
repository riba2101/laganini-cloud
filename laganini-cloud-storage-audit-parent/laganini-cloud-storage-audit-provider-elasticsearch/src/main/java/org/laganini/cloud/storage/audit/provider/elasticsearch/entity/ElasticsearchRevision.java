package org.laganini.cloud.storage.audit.provider.elasticsearch.entity;

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
public class ElasticsearchRevision implements RevisionEntity {

    @Id
    private String id;
    private String type;

    public ElasticsearchRevision(String id, String type) {
        this.id = id;
        this.type = type;
    }

}
