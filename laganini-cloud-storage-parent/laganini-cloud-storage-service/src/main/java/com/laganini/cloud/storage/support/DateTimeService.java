package com.laganini.cloud.storage.support;

import java.time.Clock;
import java.time.LocalDateTime;

public class DateTimeService {

    private final Clock          clock;
    private final DateTimeParser dateTimeParser;

    public DateTimeService(Clock clock, DateTimeParser dateTimeParser) {
        this.clock = clock;
        this.dateTimeParser = dateTimeParser;
    }

    public LocalDateTime now() {
        return LocalDateTime.now(clock);
    }

    public LocalDateTime parse(String date) {
        return dateTimeParser.parse(date, clock.getZone());
    }

}
