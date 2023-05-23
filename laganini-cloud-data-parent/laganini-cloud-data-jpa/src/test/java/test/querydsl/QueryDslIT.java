package test.querydsl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.laganini.cloud.data.jpa.test.JpaTest;
import org.laganini.cloud.data.jpa.test.MariaDbContextInitializer;
import org.laganini.cloud.data.test.FlywayExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import test.repository.BaseEntity;
import test.repository.QBaseEntity;

@JpaTest(initializers = MariaDbContextInitializer.class)
@ExtendWith(FlywayExtension.class)
class QueryDslIT {

    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private JdbcTemplate    jdbcTemplate;

    @Test
    void test() {
        jdbcTemplate.execute("INSERT INTO base VALUES(null, 'test')");

        QBaseEntity testEntity = new QBaseEntity("test");
        JPAQuery<BaseEntity> query = queryFactory
                .select(testEntity)
                .from(testEntity);

        Assertions
                .assertThat(query)
                .isNotNull();
    }

}
