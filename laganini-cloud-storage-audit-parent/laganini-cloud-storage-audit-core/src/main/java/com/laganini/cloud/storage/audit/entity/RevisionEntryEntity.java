package com.laganini.cloud.storage.audit.entity;

import com.laganini.cloud.storage.audit.dto.RevisionOperation;

import java.time.LocalDateTime;

public interface RevisionEntryEntity {

    String getId();

    long getVersion();

    String getRevisionId();

    RevisionOperation getOperation();

    String getAuthor();

    String getPrevious();

    String getAfter();

    String getDiff();

    LocalDateTime getInstant();

}
