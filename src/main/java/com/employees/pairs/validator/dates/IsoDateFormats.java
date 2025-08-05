package com.employees.pairs.validator.dates;

import java.util.List;

class IsoDateFormats extends DateParserProcessor {

    public IsoDateFormats(DateParserProcessor parser) {
        super(parser, List.of(
                "yyyy-MM-dd",        // 2024-08-01
                "yyyy/MM/dd",        // 2024/08/01
                "yyyy.MM.dd",        // 2024.08.01
                "yyyyMMdd"           // 20240801)
        ));
    }
}
