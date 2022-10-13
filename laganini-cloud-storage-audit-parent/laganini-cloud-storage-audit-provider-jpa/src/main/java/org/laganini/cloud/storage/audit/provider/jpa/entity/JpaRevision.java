package org.laganini.cloud.storage.audit.provider.jpa.entity;

import lombok.*;
import org.laganini.cloud.storage.audit.entity.RevisionEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "revision")
@Entity
public class JpaRevision implements RevisionEntity {

    @Id
    private String id;
    private String type;

    public JpaRevision(String id, String type) {
        this.id = id;
        this.type = type;
    }

}
