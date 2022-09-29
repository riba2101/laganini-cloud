package com.laganini.cloud.storage.audit.provider.jpa;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import com.laganini.cloud.storage.audit.service.AbstractRevisionService;

public class JpaRevisionService extends AbstractRevisionService<JpaRevision> {

    public JpaRevisionService(
            JpaRevisionRepository repository,
            RevisionConverter<Revision, JpaRevision> converter
    )
    {
        super(repository, converter);
    }

}
