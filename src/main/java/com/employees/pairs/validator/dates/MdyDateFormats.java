package com.employees.pairs.validator.dates;

import java.util.List;

public class MdyDateFormats extends DateParserProcessor {

    public MdyDateFormats(DateParserProcessor parser) {
        super(parser, List.of(
                "MM-dd-yyyy",        // 08-01-2024
                "MM/dd/yyyy",        // 08/01/2024
                "MM.dd.yyyy",        // 08.01.2024
                "MMddyyyy"           // 08012024
        ));
    }
}
