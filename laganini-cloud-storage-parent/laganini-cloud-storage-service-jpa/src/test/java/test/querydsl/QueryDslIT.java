package test.querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.storage.jpa.test.JpaTest;
import org.laganini.cloud.storage.test.FlywayExtension;
import org.laganini.cloud.storage.jpa.test.MariaDbContextInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import test.repository.QTestEntity;
import test.repository.TestEntity;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class QueryDslIT {

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
