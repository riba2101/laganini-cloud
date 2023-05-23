package org.laganini.cloud.test.performance;

import java.util.Map;

import lombok.Getter;

@Getter
public class SimulationContext {

    private final String baseUrl;
    private final Map<String, String> headers;
    private final Object body;

    public SimulationContext(String baseUrl, Map<String, String> headers, Object body) {
        this.baseUrl = baseUrl;
        this.headers = headers;
        this.body = body;
    }

}
