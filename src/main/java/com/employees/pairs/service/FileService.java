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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService implements FileServiceRest, FileServiceUI {

    private final LineValidator lineValidator;
    private final DateParserProcessor dateParserProcessor;

    @Override
    public String getPairs(MultipartFile file, char delimiter) {
        var lines = readAllLines(file, delimiter);
        var data = parseData(lines);
        var grouped = data.stream().collect(Collectors.groupingBy(EmployeeData::projectId));

        var transformed = transformGroupData(grouped);

        transformed.sort(Comparator.comparing(PairsResponse::days).reversed());

        log.info("{} - {} : {}", transformed.getFirst().employee1(), transformed.getFirst().employee2(),
                transformed.getFirst().days());

        var longestOverlap = transformed.getFirst();

        StringBuffer sb = new StringBuffer();
        sb.append(longestOverlap.employee1())
                .append(", ")
                .append(longestOverlap.employee2())
                .append(", ")
                .append(longestOverlap.days());

        return sb.toString();
    }

    @Override
    public void populateModelWithPairs(RedirectAttributes model, MultipartFile file, char delimiter) {
        var lines = readAllLines(file, delimiter);
        var data = parseData(lines);

        model.addFlashAttribute("list", data);

        var grouped = data.stream().collect(Collectors.groupingBy(EmployeeData::projectId));
        var transformed = transformGroupData(grouped);
        transformed.sort(Comparator.comparing(PairsResponse::days).reversed());

        model.addFlashAttribute("datagrid", transformed);

        var longestOverlap = transformed.getFirst();

        model.addFlashAttribute("longestOverlap", longestOverlap);
    }

    private List<PairsResponse> transformGroupData(Map<Integer, List<EmployeeData>> grouped) {
        List<PairsResponse> response = new ArrayList<>();

        for (var group : grouped.entrySet()) {
            var projectId = group.getKey();
            var list = group.getValue();

            if (list.size() == 1) {
                response.add(new PairsResponse(list.getFirst().employeeId(), null, projectId, 0L));
                continue;
            }

            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    var emp1 = list.get(i);
                    var emp2 = list.get(j);

                    var start = emp1.dateFrom().isAfter(emp2.dateFrom()) ? emp1.dateFrom() : emp2.dateFrom();
                    var end = emp1.dateTo().isBefore(emp2.dateTo()) ? emp1.dateTo() : emp2.dateTo();

                    if (start.isBefore(end)) {
                        var days = ChronoUnit.DAYS.between(start, end) + 1;
                        log.debug("{} - {} : {} | {}", emp1.employeeId(), emp2.employeeId(),
                                days, projectId);
                        response.add(new PairsResponse(emp1.employeeId(), emp2.employeeId(), projectId, days));
                    }
                }
            }
        }

        return response;
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
