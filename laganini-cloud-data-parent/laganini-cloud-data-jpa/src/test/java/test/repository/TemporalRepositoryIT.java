package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.jpa.entity.JpaTemporalReference;
import org.laganini.cloud.data.jpa.test.JpaTest;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class TemporalRepositoryIT {

    private final TemporalRepository testRepository;

    @Autowired
    TemporalRepositoryIT(TemporalRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        testRepository.deleteAll();

        TemporalEntity test1 = givenTemporalEntity(1L);
        TemporalEntity test2 = givenTemporalEntity(2L);

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

        TemporalEntity test1 = givenTemporalEntity(1L);
        TemporalEntity test2 = givenTemporalEntity(2L);
        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);

        List<TemporalEntity> entities = testRepository.query(query -> query
                .select(QTemporalEntity.temporalEntity)
                .from(QTemporalEntity.temporalEntity)
                .fetch()
        );
        Assertions
                .assertThat(entities)
                .hasSize(2);
        Assertions
                .assertThat(entities)
                .extracting(TemporalEntity::getTemporal)
                .extracting(JpaTemporalReference::getCreatedAt, JpaTemporalReference::getUpdatedAt)
                .doesNotContainNull();
    }

    private TemporalEntity givenTemporalEntity(long id) {
        return new TemporalEntity("test" + id, new JpaTemporalReference(LocalDateTime.now(), LocalDateTime.now()));
    }

}