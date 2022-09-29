package com.laganini.cloud.storage.audit.diff;

import java.util.Map;

public interface DiffService {

    Map read(String value);

    Map convert(Object value);

    String write(Map value);

    Map calculateDiff(Map<Object, Object> before, Map<Object, Object> after);

}
