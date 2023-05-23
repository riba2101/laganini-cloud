package test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.r2dbc.entity.R2dbcPurgeableReference;
import org.laganini.cloud.data.r2dbc.test.MariaDbContextInitializer;
import org.laganini.cloud.data.r2dbc.test.R2dbcTest;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class PurgeableRepositoryIT {

    private final PurgeableRepository testRepository;

    @Autowired
    PurgeableRepositoryIT(PurgeableRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        PurgeableEntity test1 = givenPurgeableEntity(1L);
        PurgeableEntity test2 = givenPurgeableEntity(2L);

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

    private PurgeableEntity givenPurgeableEntity(long id) {
        return new PurgeableEntity(id, "test" + id, new R2dbcPurgeableReference(LocalDateTime.now()));
    }

}