package com.employees.pairs.validator.dates;

import io.micrometer.common.util.StringUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Slf4j
public sealed class DateParserProcessor permits
        DmyDateFormats,
        IsoDateFormats,
        IsoDatetimeFormats,
        MdyDateFormats,
        ShortYearDateFormats,
        TxtMonthDateFormat {
    @Setter
    protected DateParserProcessor nextParser;
    private final List<String> formats;

    public DateParserProcessor(List<String> formats) {
        this.formats = formats;
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
                log.debug("Can not parse date `{}` with format `{}`", date, format);
            }
        }

        if (nextParser != null) {
            return nextParser.parse(date);
        }

        log.debug("Can not parse date `{}`", date);
        throw new IllegalArgumentException(
                String.format("Unable to parse date '%s' with supported formats: %s", date, formats));
    }
}
