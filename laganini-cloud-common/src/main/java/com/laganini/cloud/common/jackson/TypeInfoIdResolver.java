package com.laganini.cloud.common.jackson;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

@Slf4j
public class TypeInfoIdResolver
        implements TypeIdResolver {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(MapperFeature.DEFAULT_VIEW_INCLUSION)
            .findAndRegisterModules();

    @Override
    public void init(JavaType baseType) {

    }

    @Override
    public String idFromValue(Object value) {
        Class<?> clazz = value.getClass();
        if (Collection.class.isAssignableFrom(clazz)) {
            Collection<?> collection = (Collection<?>) value;
            if (collection.isEmpty()) {
                return build(new TypeInfo(getClassName(clazz), null));
            }

            Class<?> collectionClass = collection
                    .stream()
                    .filter(Objects::nonNull)
                    .findFirst()
                    .map(Object::getClass)
                    .orElse(null);

            return build(new TypeInfo(getClassName(clazz), getClassName(collectionClass)));
        }

        return build(new TypeInfo(getClassName(clazz), null));
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public String idFromBaseType() {
        return null;
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException {
        try {
            TypeInfo typeInfo = OBJECT_MAPPER.readValue(id, TypeInfo.class);
            Class<?> clazz = Class.forName(typeInfo.getClazz());

            if (typeInfo.getGeneric() == null) {
                return OBJECT_MAPPER.getTypeFactory().constructType(clazz);
            }

            Class<?> generic = Class.forName(typeInfo.getGeneric());
            return OBJECT_MAPPER.getTypeFactory().constructParametricType(clazz, generic);
        } catch (Exception e) {
            log.error("Error creating java type: {}", id, e);
            return null;
        }
    }

    @Override
    public String getDescForKnownTypeIds() {
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }

    private String getClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return clazz.getTypeName();
    }

    private String build(TypeInfo typeInfo) {
        try {
            return OBJECT_MAPPER.writeValueAsString(typeInfo);
        } catch (JsonProcessingException e) {
            log.error("Could not convert: {}", typeInfo, e);
            return null;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private static class TypeInfo {

        private String clazz;
        private String generic;

        private TypeInfo(String clazz, String generic) {
            this.clazz = clazz;
            this.generic = generic;
        }

    }

}
