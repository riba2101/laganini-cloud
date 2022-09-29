package test.app.jpa;

import org.springframework.data.repository.CrudRepository;

public interface DummyRepository extends CrudRepository<Dummy, Integer> {
}
