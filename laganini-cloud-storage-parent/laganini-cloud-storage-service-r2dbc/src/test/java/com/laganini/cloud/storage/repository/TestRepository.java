package org.laganini.cloud.storage.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface TestRepository
        extends R2dbcRepository<TestEntity, Integer>
{

}
