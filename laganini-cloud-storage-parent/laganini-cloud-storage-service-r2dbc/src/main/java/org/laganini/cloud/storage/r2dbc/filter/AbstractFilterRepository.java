package org.laganini.cloud.storage.r2dbc.filter;

import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.TemporalExpression;
import com.querydsl.core.util.MathUtils;
import org.laganini.cloud.storage.connector.model.Operator;
import org.laganini.cloud.storage.connector.model.*;
import org.laganini.cloud.storage.filter.AbstractSearchCriteriaMapper;
import org.laganini.cloud.storage.r2dbc.querydsl.corereactive.FetchableR2dbcQuery;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.support.DateTimeService;

import java.time.temporal.Temporal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class AbstractFilterRepository<ENTITY>
        implements FilterRepository<ENTITY>
{

    private final AbstractSearchCriteriaMapper mapper;
    private final DateTimeService              dateTimeService;

    protected AbstractFilterRepository(AbstractSearchCriteriaMapper mapper, DateTimeService dateTimeService) {
        this.mapper = mapper;
        this.dateTimeService = dateTimeService;
    }

    @Override
    public FilteredAndSorted<ENTITY> filter(Collection<FilterCriteria<?>> filters) {
        throw new UnsupportedOperationException("currently not supported");
    }

    @Override
    public FilteredAndSorted<ENTITY> filter(Collection<FilterCriteria<?>> filters, Collection<SortCriteria> sorts) {
        throw new UnsupportedOperationException("currently not supported");
    }

    @Override
    public FilteredAndSorted<ENTITY> filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts,
            int page,
            int pageSize
    )
    {
        throw new UnsupportedOperationException("currently not supported");
    }

//    @Override
//    public Mono<FilteredAndSorted<ENTITY>> filter(Collection<FilterCriteria> filters) {
//        return filter(filters, Collections.emptyList(), 0, 0);
//    }
//
//    @Override
//    public Mono<FilteredAndSorted<ENTITY>> filter(Collection<FilterCriteria> filters, Collection<SortCriteria> sorts) {
//        return filter(filters, sorts, 0, 0);
//    }
//
//    @Override
//    public Mono<FilteredAndSorted<ENTITY>> filter(
//            Collection<FilterCriteria> filters,
//            Collection<SortCriteria> sorts,
//            int page,
//            int pageSize
//    ) {
//        FetchableR2dbcQuery<ENTITY, ? extends FetchableR2dbcQuery<ENTITY, ?>> countQuery = getBaseQuery();
//
//        Map<String, FilterCriteria> countFilterPaths = mapper.getKnownFilterPaths(filters);
//        if (!countFilterPaths.isEmpty()) {
//            countFilterPaths.forEach((stringField, filterCriteria) -> {
//                AbstractSearchCriteriaMapper.Field<?> mappedField = mapper.getMapping(stringField);
//
//                buildWhere(countQuery, mappedField, filterCriteria.getValue());
//            });
//        }
//
//        return countQuery
//                .select(SQLExpressions.countAll)
//                .fetchFirst()
//                .flatMap(count -> {
//                    FetchableR2dbcQuery<ENTITY, ? extends FetchableR2dbcQuery<ENTITY, ?>> query = getBaseQuery();
//
//                    Map<String, FilterCriteria> knownFilterPaths = mapper.getKnownFilterPaths(filters);
//                    if (!knownFilterPaths.isEmpty()) {
//                        knownFilterPaths.forEach((stringField, filterCriteria) -> {
//                            AbstractSearchCriteriaMapper.Field<?> mappedField = mapper.getMapping(stringField);
//
//                            buildWhere(query, mappedField, filterCriteria.getValue());
//                        });
//                    }
//
//                    Map<String, SortCriteria> knownSortPaths = mapper.getKnownSortPaths(sorts);
//                    if (!knownSortPaths.isEmpty()) {
//                        knownSortPaths.forEach((stringField, sortCriteria) -> {
//                            AbstractSearchCriteriaMapper.Field<?> mappedField = mapper.getMapping(stringField);
//
//                            buildOrder(query, mappedField, sortCriteria.getDirection());
//                        });
//                    }
//
//                    if (pageSize > 0) {
//                        if (page > 0) {
//                            query.offset(page * (long) pageSize);
//                        }
//                        query.limit(pageSize);
//                    }
//
//                    return query
//                            .fetch()
//                            .collectList()
//                            .map(entities -> new FilteredAndSorted<>(
//                                    entities,
//                                    count,
//                                    page,
//                                    pageSize,
//                                    knownFilterPaths.values(),
//                                    knownSortPaths.values()
//                            ));
//                });
//    }

    protected abstract FetchableR2dbcQuery<ENTITY, ? extends FetchableR2dbcQuery<ENTITY, ?>> getBaseQuery();

    private void buildWhere(
            FetchableR2dbcQuery<ENTITY, ? extends FetchableR2dbcQuery<ENTITY, ?>> query,
            AbstractSearchCriteriaMapper.Field<?> fieldMapping,
            org.laganini.cloud.storage.connector.model.Operator operator,
            Object value
    )
    {
        Predicate predicate = determinePredicate(fieldMapping, operator, value);
        query.where(predicate);
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate determinePredicate(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            org.laganini.cloud.storage.connector.model.Operator operator,
            Object value
    )
    {
        Expression<T> path = fieldMapping.getField();

        if (value == null) {
            return operator == org.laganini.cloud.storage.connector.model.Operator.NOT_EQUALS ? ExpressionUtils.isNotNull(
                    path) : ExpressionUtils.isNull(path);
        }

        if (value.getClass().isArray()) {
            Set<T> casted = convert(fieldMapping.getConverter(), path, Arrays.asList((T[]) value));
            return operator == org.laganini.cloud.storage.connector.model.Operator.NOT_EQUALS ? ExpressionUtils.notIn(
                    path,
                    casted
            ) : ExpressionUtils.in(
                    path,
                    casted
            );
        }

        if (value instanceof Iterable) {
            Set<T> casted = convert(fieldMapping.getConverter(), path, (Iterable<T>) value);
            return operator == org.laganini.cloud.storage.connector.model.Operator.NOT_EQUALS ? ExpressionUtils.notIn(
                    path,
                    casted
            ) : ExpressionUtils.in(
                    path,
                    casted
            );
        }

        T         converted        = fieldMapping.getConverter().convert(value);
        Predicate appliedOperation = applyOperation(fieldMapping, operator, converted);
        if (appliedOperation != null) {
            return appliedOperation;
        }

        return ExpressionUtils.eq(
                path,
                (Expression<? extends T>) ExpressionUtils.toExpression(converted)
        );
    }

    private <T> Predicate applyOperation(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            Operator operator,
            T value
    )
    {
        Expression<T> path = fieldMapping.getField();

        return switch (operator) {
            case EQUALS -> ExpressionUtils.eq(
                    path,
                    (Expression<T>) ExpressionUtils.toExpression(value)
            );
            case NOT_EQUALS -> ExpressionUtils.ne(
                    path,
                    (Expression<T>) ExpressionUtils.toExpression(value)
            );
            case GT -> {
                if (Number.class.isAssignableFrom(value.getClass())) {
                    yield ((NumberExpression) path).gt((Number) value);
                }

                if (Temporal.class.isAssignableFrom(value.getClass())) {
                    yield ((TemporalExpression) path).gt((Comparable) value);
                }

                yield null;
            }
            case GTE -> {
                if (Number.class.isAssignableFrom(value.getClass())) {
                    yield ((NumberExpression) path).goe((Number) value);
                }

                if (Temporal.class.isAssignableFrom(value.getClass())) {
                    yield ((TemporalExpression) path).goe((Comparable) value);
                }

                yield null;
            }
            case LT -> {
                if (Number.class.isAssignableFrom(value.getClass())) {
                    yield ((NumberExpression) path).lt((Number) value);
                }

                if (Temporal.class.isAssignableFrom(value.getClass())) {
                    yield ((TemporalExpression) path).lt((Comparable) value);
                }

                yield null;
            }
            case LTE -> {
                if (Number.class.isAssignableFrom(value.getClass())) {
                    yield ((NumberExpression) path).loe((Number) value);
                }

                if (Temporal.class.isAssignableFrom(value.getClass())) {
                    yield ((TemporalExpression) path).loe((Comparable) value);
                }

                yield null;
            }
            case CONTAINS -> {
                if (String.class.isAssignableFrom(value.getClass())) {
                    yield ((StringExpression) path).contains((String) value);
                }

                yield null;
            }
            case STARTS_WITH -> {
                if (String.class.isAssignableFrom(value.getClass())) {
                    yield ((StringExpression) path).startsWith((String) value);
                }

                yield null;
            }
            case ENDS_WITH -> {
                if (String.class.isAssignableFrom(value.getClass())) {
                    yield ((StringExpression) path).endsWith((String) value);
                }

                yield null;
            }
        };
    }

    @SuppressWarnings("unchecked")
    private <T> Set<T> convert(
            AbstractSearchCriteriaMapper.FieldConverter<Object, T> fieldConverter,
            Expression<T> path,
            Iterable<T> values
    )
    {
        return (Set<T>) StreamSupport
                .stream(values.spliterator(), false)
                .map(value -> convert(fieldConverter, path, value))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private <T> Object convert(
            AbstractSearchCriteriaMapper.FieldConverter<Object, T> fieldConverter,
            Expression<T> path,
            T value
    )
    {
        return convert(fieldConverter, (Class<T>) path.getType(), value);
    }

    private <T> Object convert(
            AbstractSearchCriteriaMapper.FieldConverter<Object, T> fieldConverter,
            Class<T> clazz,
            T value
    )
    {
        if (Temporal.class.isAssignableFrom(clazz)) {
            return dateTimeService.parse((String) value);
        }

        if (Number.class.isAssignableFrom(clazz)) {
            return MathUtils.cast((Number) value, (Class<? extends Number>) clazz);
        }

        return fieldConverter.convert(value);
    }

    private void buildOrder(
            FetchableR2dbcQuery<ENTITY, ? extends FetchableR2dbcQuery<ENTITY, ?>> query,
            AbstractSearchCriteriaMapper.Field<?> fieldMapping,
            Direction direction
    )
    {
        query.orderBy(new OrderSpecifier(getOrder(direction), fieldMapping.getField()));
    }

    private Order getOrder(Direction direction) {
        switch (direction) {
            case ASC:
                return Order.ASC;
            default:
                return Order.DESC;
        }
    }

}
