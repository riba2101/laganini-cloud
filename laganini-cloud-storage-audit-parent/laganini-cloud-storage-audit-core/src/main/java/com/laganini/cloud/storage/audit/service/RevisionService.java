package com.laganini.cloud.storage.audit.service;

import com.laganini.cloud.storage.audit.dto.Revision;

import java.util.Optional;

public interface RevisionService {

    Optional<Revision> get(String name, Object id);

    Optional<Revision> get(String key);

    Optional<Revision> get(Object entity);

    Revision getOrCreate(String name, String type, Object id);

    Revision create(String name, String type, Object id);

}
