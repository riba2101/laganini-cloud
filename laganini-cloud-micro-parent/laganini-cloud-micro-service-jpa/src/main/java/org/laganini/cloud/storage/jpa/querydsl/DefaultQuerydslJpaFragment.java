package org.laganini.cloud.storage.jpa.querydsl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Transactional(readOnly = true)
public class DefaultQuerydslJpaFragment<T> implements QuerydslJpaFragment {

    private final EntityPath<T>   path;
    private final JPAQueryFactory jpaQueryFactory;
    private final EntityManager   entityManager;

    public DefaultQuerydslJpaFragment(
            EntityPath<T> path,
            JPAQueryFactory jpaQueryFactory,
            EntityManager entityManager
    )
    {
        this.path = path;
        this.jpaQueryFactory = jpaQueryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public <O> O query(Function<JPAQuery<?>, O> query) {
        return query.apply(jpaQueryFactory.query());
    }

    @Transactional
    @Override
    public long update(Function<JPAUpdateClause, Long> update) {
        return update.apply(jpaQueryFactory.update(path));
    }

    @Transactional
    @Override
    public long deleteWhere(Predicate predicate) {
        return jpaQueryFactory.delete(path).where(predicate).execute();
    }

    @Transactional
    @Override
    public <O> O executeStoredProcedure(String name, Function<StoredProcedureQueryBuilder, O> query) {
        return query.apply(new StoredProcedureQueryBuilder(name, entityManager));
    }
}
