package org.laganini.cloud.storage.audit.diff;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DefaultDiffService implements DiffService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    @Override
    public Map read(String value) {
        if (value == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(value, Map.class);
        } catch (JsonProcessingException e) {
            log.warn("Could not convert String: {} to Map", value);
            return null;
        }
    }

    @Override
    public Map convert(Object value) {
        if (value == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.convertValue(value, Map.class);
        } catch (Exception e) {
            log.warn("Could not convert object: {} to Map", value);
            return null;
        }
    }

    @Override
    public String write(Map value) {
        if (value == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.warn("Could not convert Map: {} to String", value);
            return null;
        }
    }

    @Override
    public Map calculateDiff(Map<Object, Object> before, Map<Object, Object> after) {
        if (before == null || after == null) {
            return null;
        }

        Map<Object, Object> diff = new HashMap<>(after);

        diff.entrySet().removeIf(entry -> {
            Object key   = entry.getKey();
            Object value = entry.getValue();
            return after.containsKey(key) && Objects.equals(before.get(key), value);
        });

        return diff;
    }

}
