package com.laganini.cloud.storage.audit.service;


import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.dto.RevisionEntry;
import com.laganini.cloud.storage.audit.dto.RevisionOperation;

import java.util.Collection;
import java.util.Optional;

public interface RevisionEntryService {

    Optional<RevisionEntry> getLatest(Revision revision);

    Collection<RevisionEntry> getAll(Revision revision);

    RevisionEntry create(Revision revision, RevisionOperation operation, Object idOrEntity);

}
