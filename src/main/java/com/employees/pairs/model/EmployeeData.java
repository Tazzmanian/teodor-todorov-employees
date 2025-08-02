package com.employees.pairs.model;

import java.time.LocalDate;

public record EmployeeData(Integer employeeId, Integer projectId,
                           LocalDate dateFrom, LocalDate dateTo) {
}
