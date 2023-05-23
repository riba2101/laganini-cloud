package test.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.r2dbc.entity.R2dbcOwnerReference;
import org.laganini.cloud.data.r2dbc.test.MariaDbContextInitializer;
import org.laganini.cloud.data.r2dbc.test.R2dbcTest;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.util.Arrays;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class OwnedRepositoryIT {

    private final OwnedRepository testRepository;

    @Autowired
    OwnedRepositoryIT(OwnedRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        OwnedEntity test1 = givenOwnedEntity(1L);
        OwnedEntity test2 = givenOwnedEntity(2L);

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

    private OwnedEntity givenOwnedEntity(long id) {
        return new OwnedEntity(id, "test" + id, new R2dbcOwnerReference<>(id));
    }

}