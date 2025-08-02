package com.employees.pairs.validator;


import com.employees.pairs.validator.dates.DateParserProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LineValidator {

    private final DateParserProcessor dateParserProcessor;

    public boolean validate(String[] data) {
        return data.length >= 4 && isInteger(data[0]) && isInteger(data[1])
                && dateParserProcessor.isDate(data[2]) && dateParserProcessor.isDate(data[3]);
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
