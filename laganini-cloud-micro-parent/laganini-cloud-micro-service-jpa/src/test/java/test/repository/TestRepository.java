package test.repository;

import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;

public interface TestRepository
        extends QuerydslJpaRepository<TestEntity, Long>
{

}
