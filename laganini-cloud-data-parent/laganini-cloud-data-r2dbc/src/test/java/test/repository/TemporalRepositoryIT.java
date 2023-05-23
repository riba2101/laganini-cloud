package test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.r2dbc.entity.R2dbcTemporalReference;
import org.laganini.cloud.data.r2dbc.test.MariaDbContextInitializer;
import org.laganini.cloud.data.r2dbc.test.R2dbcTest;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.Arrays;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class TemporalRepositoryIT {

    private final TemporalRepository testRepository;

    @Autowired
    TemporalRepositoryIT(TemporalRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        TemporalEntity test1 = givenTemporalEntity(1L);
        TemporalEntity test2 = givenTemporalEntity(2L);

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

    private TemporalEntity givenTemporalEntity(long id) {
        return new TemporalEntity(
                id,
                "test" + id,
                new R2dbcTemporalReference(LocalDateTime.now(), LocalDateTime.now())
        );
    }

}