package com.employees.pairs.validator.dates;

import java.util.List;

final class IsoDatetimeFormats extends DateParserProcessor {

    public IsoDatetimeFormats() {
        super(List.of(
                "yyyy-MM-dd'T'HH:mm:ss",      // 2024-08-01T14:30:00
                "yyyy-MM-dd HH:mm:ss",        // 2024-08-01 14:30:00
                "dd/MM/yyyy HH:mm:ss",        // 01/08/2024 14:30:00
                "MM-dd-yyyy HH:mm:ss"         // 08-01-2024 14:30:00
        ));
    }
}
