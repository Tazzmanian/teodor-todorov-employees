package com.employees.pairs.validator.dates;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateTimeParserConfiguration {

    @Bean
    public DmyDateFormats dmyDateFormats() {
        return new DmyDateFormats();
    }

    @Bean
    public IsoDateFormats isoDateFormats() {
        return new IsoDateFormats();
    }

    @Bean
    public IsoDatetimeFormats isoDatetimeFormats() {
        return new IsoDatetimeFormats();
    }

    @Bean
    public MdyDateFormats mdyDateFormats() {
        return new MdyDateFormats();
    }

    @Bean
    public ShortYearDateFormats shortYearDateFormats() {
        return new ShortYearDateFormats();
    }

    @Bean
    public TxtMonthDateFormat txtMonthDateFormat() {
        return new TxtMonthDateFormat();
    }

    @Bean
    public DateParserProcessor dateParserProcessor(
            DmyDateFormats dmy,
            IsoDateFormats iso,
            IsoDatetimeFormats isoDt,
            MdyDateFormats mdy,
            ShortYearDateFormats shortYear,
            TxtMonthDateFormat txtMonth) {

        dmy.setNextParser(iso);
        iso.setNextParser(isoDt);
        isoDt.setNextParser(mdy);
        mdy.setNextParser(shortYear);
        shortYear.setNextParser(txtMonth);
        return dmy;
    }
}
