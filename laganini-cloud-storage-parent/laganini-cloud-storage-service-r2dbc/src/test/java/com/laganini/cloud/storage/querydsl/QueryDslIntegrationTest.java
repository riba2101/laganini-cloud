package org.laganini.cloud.storage.querydsl;

import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.AbstractR2dbcQueryFactory;
import org.laganini.cloud.storage.r2dbc.querydsl.r2dbc.ProjectableR2dbcQuery;
import org.laganini.cloud.storage.repository.QTestEntity;
import org.laganini.cloud.test.testcontainer.MariaDBContainer;
import com.querydsl.sql.SQLExpressions;
import io.r2dbc.spi.ConnectionFactory;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.annotation.DirtiesContext;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS, methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
class QueryDslIntegrationTest {

    @Container
    private static MariaDBContainer mariaDbContainer = MariaDBContainer.getInstance()
                                                                       .whitFixatedPort(3307, 3306)
                                                                       .waitingFor(Wait.forListeningPort());
    @Autowired
    private Flyway                    flyway;
    @Autowired
    private AbstractR2dbcQueryFactory abstractR2dbcQueryFactory;
    @Autowired
    private ConnectionFactory         connectionFactory;

    @BeforeAll
    void setUp() {
        mariaDbContainer.start();
        flyway.migrate();
    }

    @AfterAll
    void destroy() {
        mariaDbContainer.stop();
    }

    @Test
    void test() {
        DatabaseClient client = DatabaseClient.create(connectionFactory);
        client
                .execute("INSERT INTO test VALUES(null, 'test', NOW(), NOW())")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        QTestEntity testEntity = new QTestEntity("test");
        ProjectableR2dbcQuery query = abstractR2dbcQueryFactory
                .select(SQLExpressions.all)
                .from(testEntity);

        StepVerifier
                .create(query.fetchFirst())
                .expectNextCount(1)
                .verifyComplete();
    }
}
