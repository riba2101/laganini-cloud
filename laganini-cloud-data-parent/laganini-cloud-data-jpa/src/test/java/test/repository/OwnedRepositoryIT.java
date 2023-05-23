package test.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.jpa.entity.JpaOwnerReference;
import org.laganini.cloud.data.jpa.test.JpaTest;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class OwnedRepositoryIT {

    private final OwnedRepository testRepository;

    @Autowired
    OwnedRepositoryIT(OwnedRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Test
    void filterAndCountRows() {
        testRepository.deleteAll();

        OwnedEntity test1 = givenOwnedEntity(1L);
        OwnedEntity test2 = givenOwnedEntity(2L);

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

        OwnedEntity test1 = givenOwnedEntity(1L);
        OwnedEntity test2 = givenOwnedEntity(2L);
        Assertions
                .assertThat(testRepository.saveAll(Arrays.asList(test1, test2)))
                .hasSize(2);

        List<OwnedEntity> entities = testRepository.query(query -> query
                .select(QOwnedEntity.ownedEntity)
                .from(QOwnedEntity.ownedEntity)
                .fetch()
        );
        Assertions
                .assertThat(entities)
                .hasSize(2);
        Assertions
                .assertThat(entities)
                .extracting(OwnedEntity::getOwner)
                .extracting(JpaOwnerReference::getOwnerId)
                .doesNotContainNull();
    }

    private OwnedEntity givenOwnedEntity(long id) {
        return new OwnedEntity("test" + id, new JpaOwnerReference<>(id));
    }

}