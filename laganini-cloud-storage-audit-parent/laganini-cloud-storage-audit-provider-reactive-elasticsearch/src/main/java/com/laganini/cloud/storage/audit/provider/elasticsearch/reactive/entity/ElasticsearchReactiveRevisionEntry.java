package com.laganini.cloud.storage.audit.provider.elasticsearch.reactive.entity;

import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "revision-entry")
public class ElasticsearchReactiveRevisionEntry implements RevisionEntryEntity {

    @Id
    private String            id;
    private String            revisionId;
    private long              version;
    private RevisionOperation operation;
    private String            author;
    private String            previous;
    private String            after;
    private String            diff;
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime     instant;

    public ElasticsearchReactiveRevisionEntry(
            String id,
            long version,
            String revisionId,
            RevisionOperation operation,
            String author,
            String previous,
            String after,
            String diff,
            LocalDateTime instant
    )
    {
        this.id = id;
        this.version = version;
        this.revisionId = revisionId;
        this.operation = operation;
        this.author = author;
        this.previous = previous;
        this.after = after;
        this.diff = diff;
        this.instant = instant;
    }
}
