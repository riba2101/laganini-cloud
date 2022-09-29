package com.laganini.cloud.storage.repository;

import com.laganini.cloud.test.testcontainer.ElasticsearchContainer;
import com.laganini.cloud.test.testcontainer.MariaDBContainer;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.test.StepVerifier;

import java.util.Arrays;

@Testcontainers
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS, methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class RepositoryIntegrationTest {

    @Container
    private static MariaDBContainer       mariaDbContainer       = MariaDBContainer
            .getInstance()
            .whitFixatedPort(3307, 3306)
            .waitingFor(Wait.forListeningPort());
    @Container
    private static ElasticsearchContainer elasticsearchContainer = ElasticsearchContainer
            .getInstance()
            .whitFixatedPort(9200, 9200)
            .whitFixatedPort(9300, 9300)
            .waitingFor(Wait.forHttp("/"));

    @Autowired
    private Flyway         flyway;
    @Autowired
    private TestRepository testRepository;

    @BeforeAll
    void setUp() {
        mariaDbContainer.start();
        elasticsearchContainer.start();
        flyway.migrate();
    }

    @AfterAll
    void destroy() {
        mariaDbContainer.stop();
        elasticsearchContainer.start();
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
                .expectNextCount(2)
                .verifyComplete();
    }

    private TestEntity givenTestEntity(long id) {
        return new TestEntity("test" + id);
    }

}