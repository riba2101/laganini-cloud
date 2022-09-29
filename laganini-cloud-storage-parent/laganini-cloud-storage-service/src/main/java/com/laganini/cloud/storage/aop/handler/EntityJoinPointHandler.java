package org.laganini.cloud.storage.aop.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Clock;
import java.time.Instant;
import java.util.Collection;


@Slf4j
public class EntityJoinPointHandler extends AbstractEntityJoinPointHandler {

    private final Clock clock;

    public EntityJoinPointHandler(Clock clock, ConversionService conversionService) {
        super(conversionService);

        this.clock = clock;
    }

    public void preSave(JoinPoint pjp) {
        Collection<?> arguments = getArguments(pjp);

        arguments.forEach(argument -> handlePreSave(argument, Instant.now(clock)));
    }

    private void handlePreSave(Object argument, Instant now) {
        Field createdDateField = ReflectionUtils.findField(argument.getClass(), CREATED_DATE_FILTER);
        if (createdDateField != null) {
            try {
                Object createdDate = readField(createdDateField, argument);
                if (createdDate == null) {
                    touchField(createdDateField, argument, now);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Cannot access annotated field for member %s on %s!",
                        createdDateField,
                        argument.getClass()
                )
                );
            }
        }

        Field lastModifiedDateField = ReflectionUtils.findField(argument.getClass(), LAST_MODIFIED_DATE_FILTER);
        if (lastModifiedDateField != null) {
            try {
                touchField(lastModifiedDateField, argument, now);
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Cannot access annotated field for member %s on %s!",
                        createdDateField,
                        argument.getClass()
                )
                );
            }
        }
    }

}
