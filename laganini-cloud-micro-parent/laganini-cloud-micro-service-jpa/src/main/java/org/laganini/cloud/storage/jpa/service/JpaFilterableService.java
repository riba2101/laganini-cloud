package org.laganini.cloud.storage.jpa.service;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.filter.FilterSupport;
import org.laganini.cloud.storage.jpa.querydsl.QuerydslJpaRepository;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Transactional(readOnly = true)
public interface JpaFilterableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslJpaRepository<ENTITY, ID>>
        extends JpaService<ID, ENTITY, REPOSITORY>, FilterableService<ID, ENTITY>
{

    @Override
    default Mono<FilteredAndSorted<ENTITY>> filter(Filter filter) {
        FilterSupport.FilterMetaData filterMetaData = getFilterSupport().build(
                filter.getFilters(),
                filter.getSorts(),
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize()
        );

        REPOSITORY      repository       = getRepositoryHolder().getRepository();
        List<Predicate> filterPredicates = filterMetaData.getFilterPredicates();

        long count = repository.query(query -> {
            JPAQuery<ENTITY> baseQuery = getRepositoryHolder().getBaseQuery(query);
            JPAQuery<ENTITY> jpaQuery = baseQuery
                    .where(filterPredicates.toArray(Predicate[]::new));

            return jpaQuery.fetchCount();
        });
        if (count == 0) {
            return Mono.just(new FilteredAndSorted<>(
                    Collections.emptyList(),
                    0L,
                    filter.getPageable().getPage(),
                    filter.getPageable().getPageSize(),
                    filterMetaData.getKnownFilterPaths(),
                    filterMetaData.getKnownSortPaths()
            ));
        }

        List<ENTITY> data = repository.query(query -> {
            JPAQuery<ENTITY> baseQuery = getRepositoryHolder().getBaseQuery(query);

            JPAQuery<ENTITY> jpaQuery = baseQuery
                    .where(filterPredicates.toArray(Predicate[]::new))
                    .orderBy(filterMetaData.getSortPredicates().toArray(OrderSpecifier[]::new));

            int limit = filterMetaData.getLimit();
            if (limit > 0) {
                jpaQuery.limit(filterMetaData.getLimit());

                long offset = filterMetaData.getOffset();
                if (offset > 0) {
                    jpaQuery.offset(offset);
                }
            }

            return jpaQuery.fetch();
        });

        return Mono.just(new FilteredAndSorted<>(
                data,
                count,
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize(),
                filterMetaData.getKnownFilterPaths(),
                filterMetaData.getKnownSortPaths()
        ));
    }

    FilterSupport getFilterSupport();

}
