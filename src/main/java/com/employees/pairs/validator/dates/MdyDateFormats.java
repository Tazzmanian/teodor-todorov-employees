package com.employees.pairs.validator.dates;

import java.util.List;

final class MdyDateFormats extends DateParserProcessor {

    public MdyDateFormats() {
        super(List.of(
                "MM-dd-yyyy",        // 08-01-2024
                "MM/dd/yyyy",        // 08/01/2024
                "MM.dd.yyyy",        // 08.01.2024
                "MMddyyyy"           // 08012024
        ));
    }
}
