package test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.r2dbc.test.MariaDbContextInitializer;
import org.laganini.cloud.data.r2dbc.test.R2dbcTest;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.Arrays;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class BaseRepositoryIT {

    private final BaseRepository testRepository;

    @Autowired
    BaseRepositoryIT(BaseRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        BaseEntity test1 = givenBaseEntity(1L);
        BaseEntity test2 = givenBaseEntity(2L);

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

    private BaseEntity givenBaseEntity(long id) {
        return new BaseEntity(id, "test" + id);
    }

}