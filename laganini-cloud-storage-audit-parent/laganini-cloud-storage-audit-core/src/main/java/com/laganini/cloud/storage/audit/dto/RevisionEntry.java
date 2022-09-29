package com.laganini.cloud.storage.audit.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RevisionEntry {

    private String            revisionId;
    private long              version;
    private RevisionOperation operation;
    private String            author;
    private String            previous;
    private String            after;
    private String            diff;
    private LocalDateTime     instant;

    public RevisionEntry(
            String revisionId,
            long version,
            RevisionOperation operation,
            String author,
            String previous,
            String after,
            String diff,
            LocalDateTime instant
    )
    {
        this.revisionId = revisionId;
        this.version = version;
        this.operation = operation;
        this.author = author;
        this.previous = previous;
        this.after = after;
        this.diff = diff;
        this.instant = instant;
    }

}
