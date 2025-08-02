package com.employees.pairs.validator.dates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateTimeParserConfiguration {

    @Bean
    public DateParserProcessor dateParserProcessor() {
        return new IsoDateFormats(
                new DmyDateFormats(
                        new MdyDateFormats(
                                new ShortYearDateFormats(
                                        new TxtMonthDateFormat(
                                                new IsoDatetimeFormats(null))))));
    }
}
