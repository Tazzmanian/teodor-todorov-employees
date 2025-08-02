package com.employees.pairs.validator.dates;

import ch.qos.logback.core.util.StringUtil;

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
        if (StringUtil.isNullOrEmpty(date)) {
            return true;
        }

        LocalDate tmp = null;
        for (var format : formats) {
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
        if (StringUtil.isNullOrEmpty(date)) {
            return LocalDate.now();
        }

        for (var format : formats) {
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
