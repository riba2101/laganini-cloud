package test.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.r2dbc.test.R2dbcTest;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.laganini.cloud.storage.r2dbc.test.MariaDbContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.Arrays;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class RepositoryIT {

    private final TestRepository testRepository;

    @Autowired
    RepositoryIT(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        TestEntity test1 = givenTestEntity(1L);
        TestEntity test2 = givenTestEntity(2L);

        StepVerifier
                .create(testRepository.saveAll(Arrays.asList(test1, test2)))
                .expectNextCount(2)
                .verifyComplete();

        StepVerifier
                .create(testRepository.findAll())
                .expectNextCount(1)
                .expectNextCount(1)
                .verifyComplete();
    }

    private TestEntity givenTestEntity(long id) {
        return new TestEntity(id, "test" + id);
    }

}