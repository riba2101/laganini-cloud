package org.laganini.cloud.storage.support;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateTimeParser {

    private final DateTimeFormatter formatter;

    public DateTimeParser() {
        /*
         * This formatter is used toConcrete parse multiple date formats, that have nested optional parts.
         * The following formats are supported:
         * 1. yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
         * 2. yyyy-MM-dd'T'HH:mm:ss.SSS(ZONE_OFFSET)
         */

        this.formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.YEAR, 4)
                .appendLiteral("-")
                .appendValue(ChronoField.MONTH_OF_YEAR, 2)
                .appendLiteral("-")
                .appendValue(ChronoField.DAY_OF_MONTH)
                .optionalStart()
                .appendLiteral("T")
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(":")
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(":")
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .optionalStart()
                .appendLiteral(".")
                .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                .optionalStart()
                .appendLiteral("Z")
                .optionalEnd()
                .optionalStart()
                .appendOffset("+HH:MM", "Z")
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
                .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
                .toFormatter();
    }

    public LocalDateTime parse(String string, ZoneId zoneId) {

        try {
            return LocalDateTime.parse(string, formatter).atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid datetime format!");
        }

    }
}
