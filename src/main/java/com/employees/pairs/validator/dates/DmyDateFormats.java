package com.employees.pairs.validator.dates;

import java.util.List;

public class DmyDateFormats extends DateParserProcessor {
    public DmyDateFormats(DateParserProcessor parser) {
        super(parser, List.of("dd-MM-yyyy",        // 01-08-2024
                "dd/MM/yyyy",        // 01/08/2024
                "dd.MM.yyyy",        // 01.08.2024
                "ddMMyyyy"           // 01082024
                ));
    }
}
