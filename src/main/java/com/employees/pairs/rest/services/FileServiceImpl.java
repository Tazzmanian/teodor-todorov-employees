package com.employees.pairs.rest.services;

import com.employees.pairs.model.EmployeeData;
import com.employees.pairs.model.PairsResponse;
import com.employees.pairs.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service("RestFileService")
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileUtils fileUtils;

    @Override
    public String getPairs(MultipartFile file, char delimiter) {
        List<EmployeeData> employeesData = fileUtils.getEmployeesData(file, delimiter);
        List<PairsResponse> pairedEmployees = fileUtils.getSortedPairedEmployees(employeesData);

        PairsResponse longestOverlap = pairedEmployees.getFirst();

        StringBuffer sb = new StringBuffer();
        sb.append(longestOverlap.employee1())
                .append(", ")
                .append(longestOverlap.employee2())
                .append(", ")
                .append(longestOverlap.days());

        return sb.toString();
    }
}
