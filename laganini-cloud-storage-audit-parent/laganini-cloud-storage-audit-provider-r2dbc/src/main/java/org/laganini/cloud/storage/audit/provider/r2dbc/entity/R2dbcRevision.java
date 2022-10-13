package org.laganini.cloud.storage.audit.provider.r2dbc.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;
import org.laganini.cloud.storage.audit.provider.r2dbc.AbstractPersistable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table("revision")
public class R2dbcRevision extends AbstractPersistable<String> implements RevisionEntity {

    @Id
    private String id;
    private String type;

    public R2dbcRevision(String id, String type) {
        this.id = id;
        this.type = type;
    }

}
