package org.laganini.cloud.storage.audit.provider.jpa.entity;

import org.laganini.cloud.storage.audit.dto.RevisionOperation;
import org.laganini.cloud.storage.audit.entity.RevisionEntryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
@Table(name = "revision_entry")
@Entity
public class JpaRevisionEntry implements RevisionEntryEntity {

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

    public JpaRevisionEntry(
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
