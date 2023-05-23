package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.jpa.test.JpaTest;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class BaseRepositoryIT {

    private final BaseRepository testRepository;

    @Autowired
    BaseRepositoryIT(BaseRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        testRepository.deleteAll();

        BaseEntity test1 = givenTestEntity(1L);
        BaseEntity test2 = givenTestEntity(2L);

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

        BaseEntity test1 = givenTestEntity(1L);
        BaseEntity test2 = givenTestEntity(2L);
        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);

        List<BaseEntity> entities = testRepository.query(query -> query
                .select(QBaseEntity.baseEntity)
                .from(QBaseEntity.baseEntity)
                .fetch()
        );
        Assertions
                .assertThat(entities)
                .hasSize(2);
    }

    private BaseEntity givenTestEntity(long id) {
        return new BaseEntity("test" + id);
    }

}