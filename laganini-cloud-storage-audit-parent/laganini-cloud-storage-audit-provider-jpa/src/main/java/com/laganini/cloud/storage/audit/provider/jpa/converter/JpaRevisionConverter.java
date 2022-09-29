package com.laganini.cloud.storage.audit.provider.jpa.converter;

import com.laganini.cloud.storage.audit.converter.RevisionConverter;
import com.laganini.cloud.storage.audit.dto.Revision;
import com.laganini.cloud.storage.audit.provider.jpa.entity.JpaRevision;

public class JpaRevisionConverter implements RevisionConverter<Revision, JpaRevision> {

    @Override
    public Revision to(JpaRevision target) {
        return new Revision(
                target.getId(),
                target.getType()
        );
    }

    @Override
    public JpaRevision from(Revision source) {
        return new JpaRevision(
                source.getKey(),
                source.getType()
        );
    }

}
