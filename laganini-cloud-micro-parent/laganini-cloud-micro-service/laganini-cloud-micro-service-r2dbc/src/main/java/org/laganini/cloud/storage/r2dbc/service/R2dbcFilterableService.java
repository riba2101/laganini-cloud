package org.laganini.cloud.storage.r2dbc.service;

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.SQLQuery;
import org.laganini.cloud.storage.connector.model.Filter;
import org.laganini.cloud.storage.connector.model.FilteredAndSorted;
import org.laganini.cloud.storage.entity.IdentityEntity;
import org.laganini.cloud.storage.filter.FilterSupport;
import org.laganini.cloud.storage.service.FilterableService;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Transactional(readOnly = true)
public interface R2dbcFilterableService<ID, ENTITY extends IdentityEntity<ID>, REPOSITORY extends QuerydslR2dbcRepository<ENTITY, ID>>
        extends R2dbcService<ID, ENTITY, REPOSITORY>, FilterableService<ID, ENTITY>
{

    @Override
    default Mono<FilteredAndSorted<ENTITY>> filter(Filter filter) {
        FilterSupport.FilterMetaData filterMetaData = getSupport().build(
                filter.getFilters(),
                filter.getSorts(),
                filter.getPageable().getPage(),
                filter.getPageable().getPageSize()
        );

        REPOSITORY repository = getRepositoryHolder().getRepository();
        return repository
                .count(ExpressionUtils.allOf(filterMetaData.getFilterPredicates()))
                .flatMap(count -> {
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

                    return repository
                            .query(query -> {
                                SQLQuery<ENTITY> baseQuery = getRepositoryHolder().getBaseQuery(query);

                                SQLQuery<ENTITY> sqlQuery = baseQuery
                                        .where(filterMetaData.getFilterPredicates().toArray(Predicate[]::new))
                                        .orderBy(filterMetaData.getSortPredicates().toArray(OrderSpecifier[]::new));

                                int limit = filterMetaData.getLimit();
                                if (limit > 0) {
                                    sqlQuery.limit(filterMetaData.getLimit());

                                    long offset = filterMetaData.getOffset();
                                    if (offset > 0) {
                                        sqlQuery.offset(offset);
                                    }
                                }

                                return sqlQuery;
                            })
                            .all()
                            .collectList()
                            .map(data -> new FilteredAndSorted<>(
                                    data,
                                    count,
                                    filter.getPageable().getPage(),
                                    filter.getPageable().getPageSize(),
                                    filterMetaData.getKnownFilterPaths(),
                                    filterMetaData.getKnownSortPaths()
                            ));
                });
    }

    FilterSupport getSupport();

}
