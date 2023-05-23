package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.jpa.entity.JpaPurgeableReference;
import org.laganini.cloud.data.jpa.test.JpaTest;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class PurgeableRepositoryIT {

    private final PurgeableRepository testRepository;

    @Autowired
    PurgeableRepositoryIT(PurgeableRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        testRepository.deleteAll();

        PurgeableEntity test1 = givenPurgeableEntity(1L);
        PurgeableEntity test2 = givenPurgeableEntity(2L);

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

        PurgeableEntity test1 = givenPurgeableEntity(1L);
        PurgeableEntity test2 = givenPurgeableEntity(2L);
        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);

        List<PurgeableEntity> entities = testRepository.query(query -> query
                .select(QPurgeableEntity.purgeableEntity)
                .from(QPurgeableEntity.purgeableEntity)
                .fetch()
        );
        Assertions
                .assertThat(entities)
                .hasSize(2);
        Assertions
                .assertThat(entities)
                .extracting(PurgeableEntity::getPurgeable)
                .extracting(JpaPurgeableReference::getDeletedAt)
                .doesNotContainNull();
    }

    private PurgeableEntity givenPurgeableEntity(long id) {
        return new PurgeableEntity("test" + id, new JpaPurgeableReference(LocalDateTime.now()));
    }

}