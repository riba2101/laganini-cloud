package org.laganini.cloud.storage.jpa.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.AttributeConverter;

@Slf4j
public class AbstractJsonConverter<T>
        implements AttributeConverter<T, String>
{

    private final ObjectMapper objectMapper;
    private final Class<T>     clazz;

    public AbstractJsonConverter(Class<T> clazz, ObjectMapper objectMapper) {
        this.clazz = clazz;
        this.objectMapper = objectMapper;
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }

        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            log.error("An error occurred while serializing payload", e);
        }

        return null;
    }

    public T convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        try {
            return objectMapper.readValue(dbData, clazz);
        } catch (Exception e) {
            log.error("An error occurred while serializing payload", e);
        }

        return null;
    }

}
