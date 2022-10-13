package test.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.laganini.cloud.storage.jpa.repository.AbstractFilterRepository;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.support.DateTimeService;

public class TestRepositoryImpl
        extends AbstractFilterRepository<TestEntity>
        implements FilterRepository<TestEntity>
{

    private final JPAQueryFactory jpaQueryFactory;

    protected TestRepositoryImpl(
            TestSearchCriteriaMapper testSearchCriteriaMapper,
            DateTimeService dateTimeService,
            JPAQueryFactory jpaQueryFactory
    )
    {
        super(testSearchCriteriaMapper, dateTimeService);

        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    protected JPAQuery<TestEntity> getBaseQuery() {
        return jpaQueryFactory
                .select(QTestEntity.testEntity)
                .from(QTestEntity.testEntity);
    }

}
