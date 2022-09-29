package test.repository;

import com.laganini.cloud.test.suite.IntegrationTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import test.TestConfiguration;
import test.TestcontainersContextInitializer;

import java.util.Arrays;

@IntegrationTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class, classes = TestConfiguration.class)
class RepositoryIntegrationTest {

    @Autowired
    private TestRepository testRepository;

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