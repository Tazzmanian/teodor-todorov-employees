package com.employees.pairs.validator.dates;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public sealed class DateParserProcessor permits
        DmyDateFormats,
        IsoDateFormats,
        IsoDatetimeFormats,
        MdyDateFormats,
        ShortYearDateFormats,
        TxtMonthDateFormat {
    protected DateParserProcessor nextParser;
    private final List<String> formats;

    public DateParserProcessor(List<String> formats) {
        this.formats = formats;
    }

    public void setNextParser(DateParserProcessor parser) {
        nextParser = parser;
    }

    public boolean isDate(String date) {
        return parse(date) != null;
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
