package org.laganini.cloud.storage.jpa.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.TemporalExpression;
import com.querydsl.core.util.MathUtils;
import com.querydsl.jpa.impl.JPAQuery;
import org.laganini.cloud.storage.connector.model.Operator;
import org.laganini.cloud.storage.connector.model.*;
import org.laganini.cloud.storage.filter.AbstractSearchCriteriaMapper;
import org.laganini.cloud.storage.repository.FilterRepository;
import org.laganini.cloud.storage.support.DateTimeService;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.temporal.Temporal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@NoRepositoryBean
public abstract class AbstractFilterRepository<ENTITY>
        extends AbstractRepository<ENTITY>
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
        return filter(filters, Collections.emptyList(), 0, 0);
    }

    @Override
    public FilteredAndSorted<ENTITY> filter(Collection<FilterCriteria<?>> filters, Collection<SortCriteria> sorts) {
        return filter(filters, sorts, 0, 0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public FilteredAndSorted<ENTITY> filter(
            Collection<FilterCriteria<?>> filters,
            Collection<SortCriteria> sorts,
            int page,
            int pageSize
    )
    {
        JPAQuery<ENTITY> query = getBaseQuery();

        Map<String, FilterCriteria<?>> knownFilterPaths = mapper.getKnownFilterPaths(filters);
        if (!knownFilterPaths.isEmpty()) {
            knownFilterPaths.forEach((stringField, filterCriteria) -> {
                AbstractSearchCriteriaMapper.Field<?> mappedField = mapper.getMapping(stringField);

                buildWhere(query, mappedField, filterCriteria.getOperator(), filterCriteria.getValue());
            });
        }

        Map<String, SortCriteria> knownSortPaths = mapper.getKnownSortPaths(sorts);
        if (!knownSortPaths.isEmpty()) {
            knownSortPaths.forEach((stringField, sortCriteria) -> {
                AbstractSearchCriteriaMapper.Field mappedField = mapper.getMapping(stringField);

                buildOrder(query, mappedField, sortCriteria.getDirection());
            });
        }

        long total = query.clone().fetchCount();

        if (pageSize > 0) {
            if (page > 0) {
                query.offset((page - 1) * (long) pageSize);
            }
            query.limit(pageSize);
        }

        QueryResults<ENTITY> queryResults = query.fetchResults();

        return new FilteredAndSorted<>(
                queryResults.getResults(),
                total,
                page,
                pageSize,
                knownFilterPaths.values(),
                knownSortPaths.values()
        );
    }

    @SuppressWarnings("unchecked")
    private void buildWhere(
            JPAQuery<ENTITY> query,
            AbstractSearchCriteriaMapper.Field<?> fieldMapping,
            Operator operator,
            Object value
    )
    {
        AbstractSearchCriteriaMapper.FieldOverride override = fieldMapping.getOverride();
        Predicate                                  predicate;

        if (override == null) {
            predicate = determinePredicate(fieldMapping, operator, value);
        } else {
            predicate = overridePredicate(fieldMapping, operator, value);
        }

        if (predicate != null) {
            query.where(predicate);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate determinePredicate(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            Operator operator,
            Object value
    )
    {
        Expression<T> path = fieldMapping.getField();

        if (value == null) {
            return operator == Operator.NOT_EQUALS ? ExpressionUtils.isNotNull(path) : ExpressionUtils.isNull(path);
        }

        if (value.getClass().isArray()) {
            Set<T> converted = convert(fieldMapping.getConverter(), path, Arrays.asList((Object[]) value));
            return applyCollectionOperation(fieldMapping, operator, converted);
        }

        if (value instanceof Iterable) {
            Set<T> converted = convert(fieldMapping.getConverter(), path, (Iterable<Object>) value);
            return applyCollectionOperation(fieldMapping, operator, converted);
        }

        Object converted = convert(fieldMapping.getConverter(), path, value);
        return applyOperation(fieldMapping, operator, converted);
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate overridePredicate(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            Operator operator,
            Object value
    )
    {
        Expression<T>                              path     = fieldMapping.getField();
        AbstractSearchCriteriaMapper.FieldOverride override = fieldMapping.getOverride();

        if (value == null) {
            return operator == Operator.NOT_EQUALS ? ExpressionUtils.isNotNull(path) : ExpressionUtils.isNull(path);
        }

        if (value.getClass().isArray()) {
            Set<T> converteds = convert(fieldMapping.getConverter(), path, Arrays.asList((Object[]) value));
            return applyCollectionOverrideOperation(override, operator, converteds);
        }

        if (value instanceof Iterable) {
            Set<T> converteds = convert(fieldMapping.getConverter(), path, (Iterable<Object>) value);
            return applyCollectionOverrideOperation(override, operator, converteds);
        }

        Object converted = convert(fieldMapping.getConverter(), path, value);
        return override.apply(converted);
    }

    private <T> Predicate applyCollectionOperation(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            Operator operator,
            Collection<T> values
    )
    {
        Set<Predicate> predicates = values
                .stream()
                .map(value -> applyOperation(fieldMapping, operator, value))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (predicates.isEmpty()) {
            return Expressions.asBoolean(true).isFalse();
        }

        if (operator == Operator.EQUALS) {
            return ExpressionUtils.anyOf(predicates);
        }

        return ExpressionUtils.allOf(predicates);
    }

    private <T> Predicate applyCollectionOverrideOperation(
            AbstractSearchCriteriaMapper.FieldOverride override,
            Operator operator,
            Set<T> converteds
    )
    {
        Set<Predicate> predicates = converteds
                .stream()
                .map((Function<T, Predicate>) override::apply)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (predicates.isEmpty()) {
            return Expressions.asBoolean(true).isFalse();
        }

        if (operator == Operator.EQUALS) {
            return ExpressionUtils.anyOf(predicates);
        }

        return ExpressionUtils.allOf(predicates);
    }

    private <T> Predicate applyOperation(
            AbstractSearchCriteriaMapper.Field<T> fieldMapping,
            Operator operator,
            Object value
    )
    {
        Expression<T> path = fieldMapping.getField();

        return switch (operator) {
            case EQUALS -> {
                if (value == null) {
                    yield ExpressionUtils.isNull(path);
                }

                yield ExpressionUtils.eq(
                        path,
                        (Expression<T>) ExpressionUtils.toExpression(value)
                );
            }
            case NOT_EQUALS -> {
                if (value == null) {
                    yield ExpressionUtils.isNotNull(path);
                }

                yield ExpressionUtils.ne(
                        path,
                        (Expression<T>) ExpressionUtils.toExpression(value)
                );
            }
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
            Iterable<Object> values
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
            Object value
    )
    {
        return convert(fieldConverter, (Class<T>) path.getType(), value);
    }

    private <T> Object convert(
            AbstractSearchCriteriaMapper.FieldConverter<Object, T> fieldConverter,
            Class<T> clazz,
            Object value
    )
    {
        if (value == null) {
            return null;
        }

        if (Temporal.class.isAssignableFrom(clazz)) {
            return dateTimeService.parse((String) value);
        }

        if (Number.class.isAssignableFrom(clazz)) {
            return MathUtils.cast((Number) value, (Class<? extends Number>) clazz);
        }

        if (fieldConverter != null) {
            return fieldConverter.convert(value);
        }

        return value;
    }

    private void buildOrder(
            JPAQuery<ENTITY> query,
            AbstractSearchCriteriaMapper.Field<? extends Comparable> fieldMapping,
            Direction direction
    )
    {
        query.orderBy(new OrderSpecifier<>(getOrder(direction), fieldMapping.getField()));
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
