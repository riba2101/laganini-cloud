package test.repository;

import org.laganini.cloud.storage.repository.FilterRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository
        extends JpaRepository<TestEntity, Long>, FilterRepository<TestEntity>
{

}
