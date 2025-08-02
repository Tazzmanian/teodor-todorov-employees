package com.employees.pairs.service;

import ch.qos.logback.core.util.StringUtil;
import com.employees.pairs.model.EmployeeData;
import com.employees.pairs.model.PairsResponse;
import com.employees.pairs.validator.LineValidator;
import com.employees.pairs.validator.dates.DateParserProcessor;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final LineValidator lineValidator;
    private final DateParserProcessor dateParserProcessor;

    public List<PairsResponse> getPairs(MultipartFile file, char delimiter) {
        var lines = readAllLines(file, delimiter);
        var data = parseData(lines);
        var grouped = data.stream().collect(Collectors.groupingBy(EmployeeData::projectId));

        return transformGroupData(grouped);
    }

    public List<PairsResponse> transformGroupData(Map<Integer, List<EmployeeData>> grouped) {

        return null;
    }

    private List<String[]> readAllLines(MultipartFile file, char delimiter) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVParser parser = new CSVParserBuilder().withSeparator(delimiter).withIgnoreQuotations(true).build();
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build())  {
                return csvReader.readAll();
            } catch (CsvException e) {
                throw new RuntimeException("Parsing csv issue");
            }
        } catch (IOException e) {
            throw new RuntimeException("File issue");
        }
    }

    private List<EmployeeData> parseData(List<String[]> lines) {
        return lines.stream().filter(lineValidator::validate)
                .map(x -> new EmployeeData(Integer.parseInt(x[0]),
                        Integer.parseInt(x[1]),
                        dateParserProcessor.parse(x[2]),
                        dateParserProcessor.parse(x[3])))
                .toList();
    }

    private LocalDate parseDate(String date) {
        if (StringUtil.isNullOrEmpty(date)) {
            return LocalDate.now();
        }

        return null;
    }

}
