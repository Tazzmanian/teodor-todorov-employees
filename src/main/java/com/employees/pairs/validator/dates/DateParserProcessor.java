package com.employees.pairs.validator.dates;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public abstract class DateParserProcessor {
    public DateParserProcessor nextParser;
    public List<String> formats;

    public DateParserProcessor(DateParserProcessor parser, List<String> formats) {
        nextParser = parser;
        this.formats = formats;
    }

    public boolean isDate(String date) {
        if (StringUtils.isBlank(date)) {
            return true;
        }

        LocalDate tmp = null;
        for (String format : formats) {
            try {
                tmp = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
                return true;
            } catch (DateTimeParseException e) {
            }
        }

        if (tmp == null && nextParser != null) {
            return nextParser.isDate(date);
        }

        return false;
    }

    public LocalDate parse(String date) {
        if (StringUtils.isBlank(date)) {
            return LocalDate.now();
        }

        for (String format : formats) {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
            } catch (DateTimeParseException e) {
            }
        }

        if (nextParser != null) {
            return nextParser.parse(date);
        }

        return null;
    }
}
