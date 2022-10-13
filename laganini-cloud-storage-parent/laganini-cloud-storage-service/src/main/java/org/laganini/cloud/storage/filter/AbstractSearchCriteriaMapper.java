package org.laganini.cloud.storage.filter;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Predicate;
import lombok.Value;
import org.laganini.cloud.storage.connector.model.FilterCriteria;
import org.laganini.cloud.storage.connector.model.SortCriteria;
import org.springframework.core.convert.converter.Converter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class AbstractSearchCriteriaMapper {

    private final Map<String, Field<?>> knownFields = new ConcurrentHashMap<>();

    public Field<?> getMapping(String filed) {
        return knownFields.get(filed);
    }

    public Map<String, FilterCriteria<?>> getKnownFilterPaths(Collection<FilterCriteria<?>> filters) {
        List<String> fields = filters
                .stream()
                .map(FilterCriteria::getField)
                .distinct()
                .collect(Collectors.toList());

        List<String> appliedKnownFields = knownFields
                .keySet()
                .stream()
                .filter(fields::contains)
                .collect(Collectors.toList());

        return filters
                .stream()
                .filter(fc -> appliedKnownFields.contains(fc.getField()))
                .collect(Collectors.toMap(
                        FilterCriteria::getField,
                        Function.identity(),
                        (p, q) -> p
                ));
    }

    public Map<String, SortCriteria> getKnownSortPaths(Collection<SortCriteria> sorts) {
        List<String> fields = sorts
                .stream()
                .map(SortCriteria::getField)
                .distinct()
                .collect(Collectors.toList());

        List<String> appliedKnownFields = knownFields
                .keySet()
                .stream()
                .filter(fields::contains)
                .collect(Collectors.toList());

        return sorts
                .stream()
                .filter(sc -> appliedKnownFields.contains(sc.getField()))
                .collect(Collectors.toMap(
                        SortCriteria::getField,
                        Function.identity(),
                        (p, q) -> p
                ));
    }

    protected <T> void registerField(String field, Expression<T> path) {
        if (path.getType().isEnum()) {
            registerField(field, path, null, new EnumFieldConverter(path.getType()));
        } else {
            registerField(field, path, null, null);
        }
    }

    protected <T> void registerField(String field, Expression<T> path, FieldOverride<T> override) {
        if (path.getType().isEnum()) {
            registerField(field, path, override, new EnumFieldConverter(path.getType()));
        } else {
            registerField(field, path, override, null);
        }
    }

    protected <T> void registerField(
            String field,
            Expression<T> path,
            FieldOverride<T> override,
            FieldConverter<Object, T> converter
    )
    {
        knownFields.put(field, new Field<>(path, override, converter));
    }

    @FunctionalInterface
    public interface FieldConverter<IN, OUT>
            extends Converter<IN, OUT>
    {
    }

    @FunctionalInterface
    public interface FieldOverride<T> {
        Predicate apply(T value);
    }

    @Value
    public class Field<T> {
        private Expression<T>             field;
        private FieldOverride<T>          override;
        private FieldConverter<Object, T> converter;
    }

}
