package test.querydsl;

import com.laganini.cloud.test.suite.IntegrationTest;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import test.TestConfiguration;
import test.TestcontainersContextInitializer;
import test.repository.QTestEntity;
import test.repository.TestEntity;

@IntegrationTest
@ContextConfiguration(initializers = TestcontainersContextInitializer.class, classes = TestConfiguration.class)
class QueryDslIntegrationTest {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private JdbcTemplate    jdbcTemplate;

    @Test
    void test() {
        jdbcTemplate.execute("INSERT INTO test VALUES(null, 'test', NOW(), NOW())");

        QTestEntity testEntity = new QTestEntity("test");
        JPAQuery<TestEntity> query = queryFactory
                .select(testEntity)
                .from(testEntity);

        Assertions
                .assertThat(query)
                .isNotNull();
    }

}
