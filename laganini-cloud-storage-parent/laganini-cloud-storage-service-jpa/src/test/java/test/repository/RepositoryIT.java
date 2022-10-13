package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.jpa.test.JpaTest;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

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

    private TestEntity givenTestEntity(long id) {
        return new TestEntity("test" + id);
    }

}