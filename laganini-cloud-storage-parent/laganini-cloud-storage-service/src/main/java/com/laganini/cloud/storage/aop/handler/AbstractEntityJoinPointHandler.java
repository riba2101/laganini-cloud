package org.laganini.cloud.storage.aop.handler;

import org.laganini.cloud.storage.aop.handler.AbstractStorageJoinPointHandler;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.util.ReflectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;

public abstract class AbstractEntityJoinPointHandler extends AbstractStorageJoinPointHandler {

    protected static final ReflectionUtils.AnnotationFieldFilter CREATED_DATE_FILTER       = new ReflectionUtils.AnnotationFieldFilter(
            CreatedDate.class);
    protected static final ReflectionUtils.AnnotationFieldFilter LAST_MODIFIED_DATE_FILTER = new ReflectionUtils.AnnotationFieldFilter(
            LastModifiedDate.class);

    private static final List<String> SUPPORTED_DATE_TYPES = List.of(
            "org.joda.time.DateTime",
            "org.joda.time.LocalDateTime",
            Date.class.getName(),
            Long.class.getName(),
            long.class.getName()
    );

    private final ConversionService conversionService;

    protected AbstractEntityJoinPointHandler(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    protected void touchField(Field field, Object argument, Instant now)
            throws InvocationTargetException, IllegalAccessException, IntrospectionException
    {
        Method writeMethod = new PropertyDescriptor(field.getName(), argument.getClass()).getWriteMethod();
        writeMethod.invoke(argument, getDateValueToSet(now, field.getType(), argument));
    }

    protected Object readField(Field field, Object argument)
            throws InvocationTargetException, IllegalAccessException, IntrospectionException
    {
        Method readMethod = new PropertyDescriptor(field.getName(), argument.getClass()).getReadMethod();
        return readMethod.invoke(argument);
    }

    private Object getDateValueToSet(TemporalAccessor value, Class<?> targetType, Object source) {
        if (TemporalAccessor.class.equals(targetType)) {
            return value;
        }

        if (conversionService.canConvert(value.getClass(), targetType)) {
            return conversionService.convert(value, targetType);
        }

        if (conversionService.canConvert(Date.class, targetType)) {
            if (!conversionService.canConvert(value.getClass(), Date.class)) {
                throw new IllegalArgumentException(String.format(
                        "Cannot convert date type for member %s! From %s to java.util.Date to %s.",
                        source,
                        value.getClass(),
                        targetType
                )
                );
            }

            Date date = conversionService.convert(value, Date.class);
            return conversionService.convert(date, targetType);
        }

        return new IllegalArgumentException(String.format(
                "Invalid date type %s for member %s! Supported types are %s.",
                source.getClass(),
                source,
                SUPPORTED_DATE_TYPES
        ));
    }

}
