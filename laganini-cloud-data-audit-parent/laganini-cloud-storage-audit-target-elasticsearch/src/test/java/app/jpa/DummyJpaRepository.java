package app.jpa;

import org.laganini.cloud.data.jpa.querydsl.QuerydslJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyJpaRepository extends QuerydslJpaRepository<DummyJpa, Integer> {

}
