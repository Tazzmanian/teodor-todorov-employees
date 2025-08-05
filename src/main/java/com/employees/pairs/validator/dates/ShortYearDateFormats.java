package com.employees.pairs.validator.dates;

import java.util.List;

class ShortYearDateFormats extends DateParserProcessor {

    public ShortYearDateFormats(DateParserProcessor parser) {
        super(parser, List.of(
                "dd-MM-yy",          // 01-08-24
                "MM/dd/yy",          // 08/01/24
                "yyMMdd"             // 240801
        ));
    }
}
