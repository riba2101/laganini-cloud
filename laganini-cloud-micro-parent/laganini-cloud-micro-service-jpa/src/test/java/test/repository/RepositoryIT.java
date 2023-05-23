package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.jpa.test.JpaTest;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class RepositoryIT {

    private final TestRepository testRepository;

    @Autowired
    RepositoryIT(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        testRepository.deleteAll();

        TestEntity test1 = givenTestEntity(1L);
        TestEntity test2 = givenTestEntity(2L);

        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);
        Assertions
                .assertThat(testRepository.findAll())
                .hasSize(2);
    }

    @Test
    void query() {
        testRepository.deleteAll();

        TestEntity test1 = givenTestEntity(1L);
        TestEntity test2 = givenTestEntity(2L);
        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);

        List<TestEntity> entities = testRepository.query(query -> query
                .select(QTestEntity.testEntity)
                .from(QTestEntity.testEntity)
                .fetch()
        );
        Assertions
                .assertThat(entities)
                .hasSize(2);
    }

    private TestEntity givenTestEntity(long id) {
        return new TestEntity("test" + id);
    }

}