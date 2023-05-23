package test.querydsl;

import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQuery;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.r2dbc.test.R2dbcTest;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.laganini.cloud.storage.r2dbc.test.MariaDbContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;
import test.repository.QTestEntity;

@R2dbcTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class QueryDslIT {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    void test() {
        DatabaseClient client = DatabaseClient.create(connectionFactory);
        client
                .sql("INSERT INTO test VALUES(null, 'test', NOW(), NOW())")
                .fetch()
                .rowsUpdated()
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();

        QTestEntity testEntity = new QTestEntity("test");
        SQLQuery query = abstractR2dbcQueryFactory
                .select(SQLExpressions.all)
                .from(testEntity);

        StepVerifier
                .create(query.fe())
                .expectNextCount(1)
                .verifyComplete();
    }

}
