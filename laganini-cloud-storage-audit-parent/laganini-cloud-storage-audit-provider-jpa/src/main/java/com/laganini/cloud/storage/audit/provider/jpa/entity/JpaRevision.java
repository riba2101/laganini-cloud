package com.laganini.cloud.storage.audit.provider.jpa.entity;

import com.laganini.cloud.storage.audit.entity.RevisionEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
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
