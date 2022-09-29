package com.laganini.cloud.storage.audit.provider.r2dbc.entity;

import com.laganini.cloud.storage.audit.dto.RevisionOperation;
import com.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import com.laganini.cloud.storage.audit.provider.r2dbc.AbstractPersistable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("revision_entry")
public class R2dbcRevisionEntry extends AbstractPersistable<String> implements RevisionEntryEntity {

    @Id
    private String            id;
    private long              version;
    private String            revisionId;
    private RevisionOperation operation;
    private String            author;
    private String            previous;
    private String            after;
    private String            diff;
    private LocalDateTime     instant;

    public R2dbcRevisionEntry(
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
