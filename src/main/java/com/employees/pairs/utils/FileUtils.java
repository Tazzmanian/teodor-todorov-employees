package com.employees.pairs.utils;

import com.employees.pairs.model.EmployeeData;
import com.employees.pairs.model.PairsResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUtils {

    List<EmployeeData> getEmployeesData(MultipartFile file, char delimiter);
    List<PairsResponse> getSortedPairedEmployees(List<EmployeeData> employeesData);
}
