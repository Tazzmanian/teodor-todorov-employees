package com.employees.pairs.service;

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
        List<EmployeeData> employeesData = getEmployeesData(file, delimiter);
        List<PairsResponse> pairedEmployees = getSortedPairedEmployees(employeesData);

        PairsResponse longestOverlap = pairedEmployees.getFirst();

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
        List<EmployeeData> employeesData = getEmployeesData(file, delimiter);

        model.addFlashAttribute("list", employeesData);

        List<PairsResponse> pairedEmployees = getSortedPairedEmployees(employeesData);

        model.addFlashAttribute("datagrid", pairedEmployees);

        PairsResponse longestOverlap = pairedEmployees.getFirst();

        model.addFlashAttribute("longestOverlap", longestOverlap);
    }

    private List<EmployeeData> getEmployeesData(MultipartFile file, char delimiter) {
        List<String[]> lines = readAllLines(file, delimiter);
        return parseEmployeeData(lines);
    }

    private List<PairsResponse> getSortedPairedEmployees(List<EmployeeData> employeesData) {
        Map<Integer, List<EmployeeData>> groupEmployeesByProjectId =
                employeesData.stream().collect(Collectors.groupingBy(EmployeeData::projectId));

        List<PairsResponse> pairedEmployees = getPairResponseFromGroupedEmployeeData(groupEmployeesByProjectId);

        pairedEmployees.sort(Comparator.comparing(PairsResponse::days).reversed());

        log.info("{} - {} : {}", pairedEmployees.getFirst().employee1(), pairedEmployees.getFirst().employee2(),
                pairedEmployees.getFirst().days());

        return pairedEmployees;
    }

    private List<PairsResponse> getPairResponseFromGroupedEmployeeData(Map<Integer, List<EmployeeData>> grouped) {
        List<PairsResponse> response = new ArrayList<>();

        for (Map.Entry<Integer, List<EmployeeData>> group : grouped.entrySet()) {
            int projectId = group.getKey();
            List<EmployeeData> employeesData = group.getValue();

            if (employeesData.size() == 1) {
                response.add(new PairsResponse(employeesData.getFirst().employeeId(), null, projectId, 0L));
                continue;
            }

            for (int i = 0; i < employeesData.size(); i++) {
                for (int j = i + 1; j < employeesData.size(); j++) {
                    EmployeeData emp1 = employeesData.get(i);
                    EmployeeData emp2 = employeesData.get(j);

                    LocalDate startDate = emp1.dateFrom().isAfter(emp2.dateFrom()) ? emp1.dateFrom() : emp2.dateFrom();
                    LocalDate endDate = emp1.dateTo().isBefore(emp2.dateTo()) ? emp1.dateTo() : emp2.dateTo();

                    if (startDate.isBefore(endDate) ||
                            startDate.isEqual(endDate)) {
                        long days = ChronoUnit.DAYS.between(startDate, endDate) + 1;
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

    private List<EmployeeData> parseEmployeeData(List<String[]> lines) {
        return lines.stream().filter(lineValidator::validate)
                .map(x -> new EmployeeData(Integer.parseInt(x[0]),
                        Integer.parseInt(x[1]),
                        dateParserProcessor.parse(x[2]),
                        dateParserProcessor.parse(x[3])))
                .toList();
    }
}
