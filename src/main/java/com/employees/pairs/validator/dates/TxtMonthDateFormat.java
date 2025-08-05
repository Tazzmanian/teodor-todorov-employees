package com.employees.pairs.validator.dates;

import java.util.List;

final class TxtMonthDateFormat extends DateParserProcessor {

    public TxtMonthDateFormat() {
        super(List.of(
                "dd-MMM-yyyy",       // 01-Aug-2024
                "dd-MMMM-yyyy",      // 01-August-2024
                "MMM dd, yyyy",      // Aug 01, 2024
                "MMMM dd, yyyy"      // August 01, 2024
        ));
    }
}
