package org.laganini.cloud.storage.audit.provider.jpa;

import org.laganini.cloud.storage.audit.converter.RevisionConverter;
import org.laganini.cloud.storage.audit.dto.Revision;
import org.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;
import org.laganini.cloud.storage.audit.service.AbstractRevisionService;

public class JpaRevisionService extends AbstractRevisionService<JpaRevision> {

    public JpaRevisionService(
            JpaRevisionRepository repository,
            RevisionConverter<Revision, JpaRevision> converter
    )
    {
        super(repository, converter);
    }

}
